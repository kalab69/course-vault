package frontendController;

import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class PdfViewerController implements Initializable {

    // ── FXML fields ───────────────────────────────────────────────────
    @FXML
    private WebView pdfWebView;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Label loadingLabel;
    @FXML
    private HBox loadingBar;
    @FXML
    private BorderPane viewerRoot;

    // ── State ─────────────────────────────────────────────────────────
    private WebEngine engine;
    private File tempPdfFile;
    private HttpServer localServer;
    private ExecutorService threadPool;
    private int serverPort = 18080;

    // ── Caches — read each file only once ─────────────────────────────
    private byte[] cachedPdfBytes = null;
    private final Map<String, byte[]> staticFileCache = new HashMap<>();

    // ── Server already started flag ───────────────────────────────────
    private boolean serverStarted = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        engine = pdfWebView.getEngine();
        engine.setJavaScriptEnabled(true);

        engine.getLoadWorker().stateProperty().addListener(
                (obs, oldState, newState) -> {
                    switch (newState) {
                        case RUNNING:
                            if (loadingBar != null) {
                                loadingBar.setVisible(true);
                            }
                            break;
                        case SUCCEEDED:
                            if (loadingBar != null) {
                                loadingBar.setVisible(false);
                            }
                            break;
                        case FAILED:
                            if (loadingLabel != null) {
                                loadingLabel.setText("Failed to load viewer.");
                                loadingLabel.setStyle("-fx-text-fill: #ef4444;");
                            }
                            break;
                        default:
                            break;
                    }
                }
        );
    }

    // ── Open from resources ───────────────────────────────────────────
    public void openResourcePdf(String resourcePath) {

        // Run extraction on background thread — never block JavaFX thread
        new Thread(() -> {
            try {
                InputStream is = getClass().getResourceAsStream(resourcePath);
                if (is == null) {
                    System.err.println("Resource not found: " + resourcePath);
                    return;
                }

                String fileName = resourcePath.substring(
                        resourcePath.lastIndexOf("/") + 1);

                tempPdfFile = File.createTempFile("cv_", "_" + fileName);
                tempPdfFile.deleteOnExit();
                Files.copy(is, tempPdfFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                is.close();

                Platform.runLater(() -> {
                    if (fileNameLabel != null) {
                        fileNameLabel.setText(fileName);
                    }
                });

                // Start server only once
                if (!serverStarted) {
                    startServer(tempPdfFile);
                } else {
                    // Server running — just update cached bytes and reload
                    cachedPdfBytes = Files.readAllBytes(tempPdfFile.toPath());
                    Platform.runLater(this::loadViewer);
                }

            } catch (IOException e) {
                System.err.println("Extraction failed: " + e.getMessage());
            }
        }).start();
    }

    // ── Open from disk file ───────────────────────────────────────────
    public void openLocalFile(File file) {
        if (file == null || !file.exists()) {
            System.err.println("File not found.");
            return;
        }

        Platform.runLater(() -> {
            if (fileNameLabel != null) {
                fileNameLabel.setText(file.getName());
            }
        });

        new Thread(() -> {
            try {
                cachedPdfBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                System.err.println("Failed to read file: " + e.getMessage());
                return;
            }

            if (!serverStarted) {
                startServer(file);
            } else {
                Platform.runLater(this::loadViewer);
            }
        }).start();
    }

    // ── Start local HTTP server — called only ONCE per viewer ─────────
    private void startServer(File pdfFile) {

        // Guard — never start twice
        if (serverStarted) {
            System.out.println("Server already running on port " + serverPort);
            Platform.runLater(this::loadViewer);
            return;
        }

        try {
            // Read PDF bytes into cache
            if (cachedPdfBytes == null) {
                cachedPdfBytes = Files.readAllBytes(pdfFile.toPath());
            }

            // Find a free port
            HttpServer server = null;
            for (int port = 18080; port < 18100; port++) {
                try {
                    server = HttpServer.create(
                            new InetSocketAddress("localhost", port), 0);
                    serverPort = port;
                    break;
                } catch (IOException e) {
                    // Port busy — try next
                }
            }

            if (server == null) {
                System.err.println("No free port found between 18080-18100.");
                return;
            }

            localServer = server;

            // Thread pool — 4 threads for parallel requests
            threadPool = Executors.newFixedThreadPool(4);
            localServer.setExecutor(threadPool);

            // ── /pdf endpoint — serve PDF from cache ─────────────────
            final byte[] pdfBytes = cachedPdfBytes;
            localServer.createContext("/pdf", exchange -> {
                try {
                    exchange.getResponseHeaders().set(
                            "Content-Type", "application/pdf");
                    exchange.getResponseHeaders().set(
                            "Access-Control-Allow-Origin", "*");
                    exchange.getResponseHeaders().set(
                            "Cache-Control", "no-cache");
                    exchange.sendResponseHeaders(200, pdfBytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(pdfBytes);
                    os.close();
                } catch (IOException e) {
                    System.err.println("PDF serve error: " + e.getMessage());
                }
            });

            // ── /pdfjs endpoint — serve static files from cache ───────
            localServer.createContext("/pdfjs", exchange -> {
                String requestPath = exchange.getRequestURI().getPath();
                try {
                    byte[] fileBytes;

                    // Check cache first
                    if (staticFileCache.containsKey(requestPath)) {
                        fileBytes = staticFileCache.get(requestPath);
                    } else {
                        InputStream is
                                = getClass().getResourceAsStream(requestPath);
                        if (is == null) {
                            String msg = "Not found: " + requestPath;
                            exchange.sendResponseHeaders(404, msg.length());
                            exchange.getResponseBody().write(msg.getBytes());
                            exchange.getResponseBody().close();
                            return;
                        }
                        fileBytes = is.readAllBytes();
                        is.close();
                        // Cache it
                        staticFileCache.put(requestPath, fileBytes);
                    }

                    String ct = getContentType(requestPath);
                    exchange.getResponseHeaders().set("Content-Type", ct);
                    exchange.getResponseHeaders().set(
                            "Access-Control-Allow-Origin", "*");
                    exchange.getResponseHeaders().set(
                            "Cache-Control", "public, max-age=3600");
                    exchange.sendResponseHeaders(200, fileBytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(fileBytes);
                    os.close();

                } catch (IOException e) {
                    System.err.println(
                            "Static serve error: " + e.getMessage());
                }
            });

            localServer.start();
            serverStarted = true;
            System.out.println("PDF server started on port " + serverPort);

            // Pre-warm cache in background
            prewarmCache();

            // Load the viewer
            Platform.runLater(this::loadViewer);

        } catch (IOException e) {
            System.err.println("Server start failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ── Load PDF.js viewer in WebView ─────────────────────────────────
private void loadViewer() {
    String pdfUrl      = "http://localhost:" + serverPort + "/pdf";
    String viewerUrl   = "http://localhost:" + serverPort
                       + "/pdfjs/web/viewer.html?file=" + pdfUrl;

    System.out.println("Loading viewer: " + viewerUrl);
    engine.load(viewerUrl);
}

    // ── Pre-warm: cache key PDF.js files in background ────────────────
    private void prewarmCache() {
        new Thread(() -> {
            String[] paths = {
                "/pdfjs/web/viewer.html",
                "/pdfjs/web/viewer.js",
                "/pdfjs/web/viewer.css",
                "/pdfjs/build/pdf.mjs",
                "/pdfjs/build/pdf.worker.mjs"
            };
            for (String path : paths) {
                if (!staticFileCache.containsKey(path)) {
                    try {
                        InputStream is
                                = getClass().getResourceAsStream(path);
                        if (is != null) {
                            staticFileCache.put(path, is.readAllBytes());
                            is.close();
                        }
                    } catch (IOException e) {
                        // Non-fatal — will be loaded on demand
                    }
                }
            }
            System.out.println("Pre-warm complete.");
        }).start();
    }

    // ── Content type helper ───────────────────────────────────────────
    private String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        }
        if (path.endsWith(".js")) {
            return "application/javascript";
        }
        if (path.endsWith(".mjs")) {
            return "application/javascript";
        }
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (path.endsWith(".png")) {
            return "image/png";
        }
        if (path.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (path.endsWith(".ico")) {
            return "image/x-icon";
        }
        if (path.endsWith(".map")) {
            return "application/json";
        }
        if (path.endsWith(".json")) {
            return "application/json";
        }
        if (path.endsWith(".woff")) {
            return "font/woff";
        }
        if (path.endsWith(".woff2")) {
            return "font/woff2";
        }
        if (path.endsWith(".ttf")) {
            return "font/ttf";
        }
        if (path.endsWith(".xml")) {
            return "application/xml";
        }
        return "application/octet-stream";
    }

    // ── Close viewer and clean up all resources ───────────────────────
    @FXML
    public void closeViewer() {
        cleanup();
        Stage stage = (Stage) viewerRoot.getScene().getWindow();
        stage.close();
    }

    // ── Called when stage closes — must release all resources ─────────
    public void cleanup() {
        // Stop server
        if (localServer != null) {
            localServer.stop(0);
            localServer = null;
            serverStarted = false;
            System.out.println("PDF server stopped.");
        }

        // Shutdown thread pool
        if (threadPool != null) {
            threadPool.shutdownNow();
            threadPool = null;
        }

        // Clear caches to free memory
        cachedPdfBytes = null;
        staticFileCache.clear();

        // Delete temp file
        if (tempPdfFile != null && tempPdfFile.exists()) {
            tempPdfFile.delete();
            tempPdfFile = null;
        }
    }
}
