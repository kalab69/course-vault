package frontendController;

import com.mycompany.model.courseModel;
import com.mycompany.model.courseResourceModel;
import com.mycompany.service.courseResourceService;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CoursePageController implements Initializable {

    // ── FXML fields ───────────────────────────────────────────────────
    @FXML
    private Label courseCode;
    @FXML
    private Label courseTitle;
    @FXML
    private VBox notesContainer;
    @FXML
    private BorderPane courseRoot;
    @FXML
    private Button notesBtn;
    @FXML
    private Button externalBtn;
    @FXML
    private Button finalBtn;
    @FXML
    private Button midtermBtn;
    @FXML
    private Button allBtn;
    @FXML
    private Button aiBtn;

    // ── State ─────────────────────────────────────────────────────────
    private courseModel course;

    // ✅ Single list — populated once from API, filtered by tab clicks
    private List<courseResourceModel> allResources = new ArrayList<>();

    // ✅ Track downloaded files — key = courseId_resourceId
    private final Map<String, File> downloadedFiles = new HashMap<>();

    // ── Active filter tab tracking ────────────────────────────────────
    private Button activeTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Wire filter tab buttons
        allBtn.setOnAction(e -> {
            setActiveTab(allBtn);
            showResources(null);
        });
        notesBtn.setOnAction(e -> {
            setActiveTab(notesBtn);
            showResources("NOTES");
        });
        midtermBtn.setOnAction(e -> {
            setActiveTab(midtermBtn);
            showResources("MIDTERM");
        });
        finalBtn.setOnAction(e -> {
            setActiveTab(finalBtn);
            showResources("FINAL");
        });
        externalBtn.setOnAction(e -> {
            setActiveTab(externalBtn);
            showResources("LINK");
        });
        aiBtn.setOnAction(e -> {
            setActiveTab(aiBtn);
            showResources("AI Summary");
        });

        // Start with "All" tab active
        setActiveTab(allBtn);
    }

    // ── Highlight the active tab button ──────────────────────────────
    private void setActiveTab(Button selected) {
        // Reset all tabs to inactive style
        Button[] tabs = {allBtn, notesBtn, midtermBtn, finalBtn, externalBtn, aiBtn};
        for (Button tab : tabs) {
            if (tab != null) {
                tab.setStyle(
                        "-fx-background-color: #1e293b;"
                        + "-fx-border-color: #334155;"
                        + "-fx-border-radius: 20;"
                        + "-fx-background-radius: 20;"
                        + "-fx-text-fill: #94a3b8;"
                        + "-fx-font-size: 12px;"
                        + "-fx-padding: 6 14 6 14;"
                        + "-fx-cursor: hand;"
                );
            }
        }
        // Highlight the selected tab
        if (selected != null) {
            selected.setStyle(
                    "-fx-background-color: #312e81;"
                    + "-fx-border-color: #6366f1;"
                    + "-fx-border-radius: 20;"
                    + "-fx-background-radius: 20;"
                    + "-fx-text-fill: #a5b4fc;"
                    + "-fx-font-size: 12px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-padding: 6 14 6 14;"
                    + "-fx-cursor: hand;"
            );
        }
        activeTab = selected;
    }

    // ── Called from Scene1Controller when a course card is clicked ────
    public void setCourse(courseModel course) {
        this.course = course;

        // Set title and code
        courseTitle.setText(course.getCourseName());
        courseCode.setText(course.getCode());

        // Set tag chip color based on year level
        if (course.getYearLevel() != null) {
            switch (course.getYearLevel().name()) {
                case "FIRST":
                    courseCode.setStyle(
                            "-fx-background-color: #312e81;"
                            + "-fx-text-fill: #a5b4fc;"
                            + "-fx-font-size: 11px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 6;"
                            + "-fx-padding: 4 12 4 12;");
                    break;
                case "SECOND":
                    courseCode.setStyle(
                            "-fx-background-color: #14532d;"
                            + "-fx-text-fill: #86efac;"
                            + "-fx-font-size: 11px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 6;"
                            + "-fx-padding: 4 12 4 12;");
                    break;
                case "THIRD":
                    courseCode.setStyle(
                            "-fx-background-color: #7c2d12;"
                            + "-fx-text-fill: #fdba74;"
                            + "-fx-font-size: 11px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 6;"
                            + "-fx-padding: 4 12 4 12;");
                    break;
                case "FOURTH":
                    courseCode.setStyle(
                            "-fx-background-color: #164e63;"
                            + "-fx-text-fill: #67e8f9;"
                            + "-fx-font-size: 11px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 6;"
                            + "-fx-padding: 4 12 4 12;");
                    break;
                default:
                    courseCode.setStyle(
                            "-fx-background-color: #1e293b;"
                            + "-fx-text-fill: #94a3b8;"
                            + "-fx-font-size: 11px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 6;"
                            + "-fx-padding: 4 12 4 12;");
                    break;
            }
        }

        // Load all resources from API — populates allResources list
        loadResources();
    }

    // ── Load ALL resources once from API ─────────────────────────────
    private void loadResources() {
        notesContainer.getChildren().clear();

        // Show loading indicator
        Label loading = new Label("Loading resources...");
        loading.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        notesContainer.getChildren().add(loading);

        new Thread(() -> {
            try {
                courseResourceService serviceInstance = new courseResourceService();
                // ✅ Fetch all resources for this course
                List<courseResourceModel> fetched = serviceInstance.fetchCourseResources(course.getId());
                Platform.runLater(() -> {
                    notesContainer.getChildren().clear();

                    if (fetched == null || fetched.isEmpty()) {
                        Label empty = new Label("No resources available.");
                        empty.setStyle(
                                "-fx-text-fill: #64748b; -fx-font-size: 12px;");
                        notesContainer.getChildren().add(empty);
                        return;
                    }

                    // ✅ Store in allResources for filtering
                    allResources = fetched;

                    // Show all resources initially (All tab)
                    showResources(null);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    notesContainer.getChildren().clear();
                    Label err = new Label("Failed to load resources.");
                    err.setStyle(
                            "-fx-text-fill: #ef4444; -fx-font-size: 12px;");
                    notesContainer.getChildren().add(err);
                });
            }
        }).start();
    }

    // ── Filter and display resources by type ─────────────────────────
    // filter = null → show all
    // filter = "NOTES", "MIDTERM", "FINAL", "LINK" → show matching
    private void showResources(String filter) {
        notesContainer.getChildren().clear();

        if (allResources == null || allResources.isEmpty()) {
            Label empty = new Label("No resources available.");
            empty.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
            notesContainer.getChildren().add(empty);
            return;
        }

        boolean anyShown = false;

        for (courseResourceModel resource : allResources) {
            // Apply filter — skip if type doesn't match
            if (filter != null
                    && !resource.getType().equalsIgnoreCase(filter)) {
                continue;
            }

            Button card = createResourceCard(resource);
            notesContainer.getChildren().add(card);
            anyShown = true;
        }

        // Show message if filter returned nothing
        if (!anyShown) {
            Label none = new Label("No "
                    + (filter != null ? filter.toLowerCase() : "")
                    + " resources available.");
            none.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
            notesContainer.getChildren().add(none);
        }
    }

    // ── Create a clickable resource card button ───────────────────────
    private Button createResourceCard(courseResourceModel resource) {
        // Choose icon based on type
        String icon = "📄";
        if (resource.getType() != null) {
            switch (resource.getType().toUpperCase()) {
                case "MIDTERM":
                    icon = "📝";
                    break;
                case "FINAL":
                    icon = "📑";
                    break;
                case "LINK":
                    icon = "🔗";
                    break;
                default:
                    icon = "📄";
                    break;
            }
        }

        Button card = new Button(icon + "  " + resource.getTitle());
        card.setMaxWidth(Double.MAX_VALUE);
        card.setAlignment(Pos.CENTER_LEFT);

        String baseStyle
                = "-fx-background-color: #1e293b;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #94a3b8;"
                + "-fx-font-size: 13px;"
                + "-fx-padding: 12 16 12 16;"
                + "-fx-cursor: hand;";

        String hoverStyle
                = "-fx-background-color: #273344;"
                + "-fx-border-color: #6366f1;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #f1f5f9;"
                + "-fx-font-size: 13px;"
                + "-fx-padding: 12 16 12 16;"
                + "-fx-cursor: hand;";

        String downloadedStyle
                = "-fx-background-color: #14532d;"
                + "-fx-border-color: #22c55e;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #86efac;"
                + "-fx-font-size: 13px;"
                + "-fx-padding: 12 16 12 16;"
                + "-fx-cursor: hand;";

        // Check if already downloaded — show green style immediately
        String resourceKey = course.getId() + "_" + resource.getId();
        File courseVaultFolder = getCourseVaultFolder();
        File existingFile = new File(courseVaultFolder, getSafeFileName(resource));

        if (existingFile.exists()
                || downloadedFiles.containsKey(resourceKey)) {
            card.setStyle(downloadedStyle);
            card.setText("✅  " + resource.getTitle());
        } else {
            card.setStyle(baseStyle);
        }

        card.setOnMouseEntered(e -> {
            if (!card.getText().startsWith("✅")
                    && !card.getText().startsWith("⬇")) {
                card.setStyle(hoverStyle);
            }
        });
        card.setOnMouseExited(e -> {
            if (!card.getText().startsWith("✅")
                    && !card.getText().startsWith("⬇")) {
                card.setStyle(baseStyle);
            }
        });

        // Click handler
        card.setOnAction(e
                -> handleResourceClick(resource, card,
                        baseStyle, downloadedStyle));

        return card;
    }

    // ── Smart click: open if downloaded, Save As if not ──────────────
    private void handleResourceClick(
            courseResourceModel resource,
            Button card,
            String baseStyle,
            String downloadedStyle) {

        String resourceKey = course.getId() + "_" + resource.getId();

        // 1. Check CourseVault folder on disk
        File courseVaultFolder = getCourseVaultFolder();
        String safeName = getSafeFileName(resource);
        File existingFile = new File(courseVaultFolder, safeName);

        if (existingFile.exists()) {
            String fileName = existingFile.getName().toLowerCase();
            if (isImageResource(resource)) {
                openImageViewer(existingFile);
            } else {
                System.out.println("✅ Already downloaded, opening: "
                        + existingFile.getAbsolutePath());
                openFile(existingFile);
            }
            return;
        }
        // 2. Check in-memory session cache
        if (downloadedFiles.containsKey(resourceKey)) {
            File cachedFile = downloadedFiles.get(resourceKey);
            if (cachedFile.exists()) {
                openFile(cachedFile);
                return;
            }
        }

        // 3. Not downloaded — show Save As dialog
        downloadWithSaveDialog(resource, resourceKey, card,
                baseStyle, downloadedStyle);
    }

    private boolean isImageResource(
            courseResourceModel resource) {
        if (resource.getType() == null) {
            return false;
        }
        return resource.getType().equalsIgnoreCase("MIDTERM")
                || resource.getType().equalsIgnoreCase("FINAL");
    }
    // ── Save As dialog — like browser save image ──────────────────────

    private void downloadWithSaveDialog(
            courseResourceModel resource,
            String resourceKey,
            Button card,
            String baseStyle,
            String downloadedStyle) {

        FileChooser fileChooser = new FileChooser();
        if (isImageResource(resource)) {
            fileChooser.setTitle("Save Image");
        } else {
            fileChooser.setTitle("Save PDF");
        }

        fileChooser.setInitialFileName(getSafeFileName(resource));

        // Default to Downloads/CourseVault
        File defaultFolder = getCourseVaultFolder();
        if (!defaultFolder.exists()) {
            defaultFolder.mkdirs();
        }
        fileChooser.setInitialDirectory(defaultFolder);
        if (isImageResource(resource)) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        } else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        }

        Stage stage = (Stage) courseRoot.getScene().getWindow();
        File destination = fileChooser.showSaveDialog(stage);

        if (destination == null) {
            return; // user cancelled
        }
        // Disable card while downloading
        card.setDisable(true);
        card.setText("⬇ Downloading...");

        final File finalDest = destination;

        new Thread(() -> {
            try {
                // Fetch from API
                String apiUrl = "http://localhost:8080/api/course-resources/"
                        + resource.getId() + "/download";

                java.net.http.HttpClient client
                        = java.net.http.HttpClient.newHttpClient();
                java.net.http.HttpRequest request
                        = java.net.http.HttpRequest.newBuilder()
                                .uri(java.net.URI.create(apiUrl))
                                .GET()
                                .build();

                java.net.http.HttpResponse<InputStream> response
                        = client.send(request,
                                java.net.http.HttpResponse.BodyHandlers
                                        .ofInputStream());

                if (response.statusCode() == 200) {
                    Files.copy(response.body(), finalDest.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    // Cache so next click opens directly
                    downloadedFiles.put(resourceKey, finalDest);

                    Platform.runLater(() -> {
                        card.setDisable(false);
                        card.setText("✅ " + resource.getTitle());
                        card.setStyle(downloadedStyle);
                        String fileName = finalDest.getName().toLowerCase();
                        if (isImageResource(resource)) {
                            openImageViewer(finalDest);
                        } else {
                            openFile(finalDest);
                        }
                    });
                } else {
                    throw new Exception("Server returned: "
                            + response.statusCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    card.setDisable(false);
                    card.setText(resource.getTitle());
                    card.setStyle(baseStyle);
                    showErrorAlert("Download Failed",
                            "Could not download the file.\n" + e.getMessage());
                });
            }
        }).start();
    }

    // ── Open with system default app ─────────────────────────────────
    private void openFile(File file) {
        new Thread(() -> {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    Platform.runLater(() -> showErrorAlert(
                            "Cannot Open",
                            "Desktop not supported on this system."));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> showErrorAlert(
                        "Cannot Open",
                        "Failed to open the file.\n" + e.getMessage()));
            }
        }).start();
    }

    // ── Helpers ───────────────────────────────────────────────────────
    private File getCourseVaultFolder() {
        return new File(
                System.getProperty("user.home")
                + File.separator + "Downloads",
                "CourseVault"
        );
    }

    private String getSafeFileName(courseResourceModel resource) {
        String extension = getExtension(resource.getFileName());
        return resource.getId() + "_" + resource.getTitle().replaceAll("[^a-zA-Z0-9\\s\\-_]", "").replaceAll("\\s+", "_") + extension;
    }

    private String getExtension(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return ".pdf";
        }
        int dot = fileName.lastIndexOf('.');
        if (dot == -1) {
            return ".pdf";
        }
        return fileName.substring(dot);
    }

    private void showErrorAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) courseRoot.getScene().getWindow();
        stage.close();
    }

    private void openImageViewer(File imageFile) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML1/ImageViewer.fxml"));
            Parent root = loader.load();
            ImageViewerController controller = loader.getController();
            controller.loadImage(imageFile);
            Stage stage = new Stage();
            stage.setTitle(imageFile.getName());
            stage.setScene(new Scene(root, 1000, 700));

            Image appIcon = new Image(getClass().getResourceAsStream("/Images/Logo.png"));
            stage.getIcons().add(appIcon);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
