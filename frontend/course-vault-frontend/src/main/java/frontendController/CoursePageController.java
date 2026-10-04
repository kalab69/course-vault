package frontendController;

import com.mycompany.model.courseModel;
import com.mycompany.model.courseResourceModel;
import com.mycompany.model.externalLinkModel;
import com.mycompany.service.courseResourceService;
import com.mycompany.service.externalLinkService;
import com.mycompany.theme.themeManager;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CoursePageController implements Initializable {

    // ── FXML fields 
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
    private boolean aiSummaryLoaded = false;
    private String cachedAiSummary = null;

    // ── State 
    private courseModel course;

    //Single list that are populated once from API, filtered by tab clicks
    private List<courseResourceModel> allResources = new ArrayList<>();

    //Track downloaded files which is key = courseId_resourceId
    private final Map<String, File> downloadedFiles = new HashMap<>();

    // ── Active filter tab tracking 
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
            loadExternalLinksSection(currentCourseId);
        });
        aiBtn.setOnAction(e -> {
            setActiveTab(aiBtn);
            showAiSummary();
        });

        // Start with "All" tab active
        setActiveTab(allBtn);

        courseRoot.sceneProperty().addListener(
                (obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        applyCurrentTheme();
                    }
                });
        themeListener = this::applyCurrentTheme;
        themeManager.addListener(themeListener);
    }

    // ── Highlight the active tab button 
    private void setActiveTab(Button selected) {
        // Reset all tabs to inactive style
        Button[] tabs = {allBtn, notesBtn, midtermBtn, finalBtn, externalBtn, aiBtn};
        for (Button tab : tabs) {
            if (tab != null) {
                tab.getStyleClass().remove("course-page-tab-btn");
                tab.getStyleClass().remove("course-page-tab-btn-selected");

                tab.getStyleClass().add("course-page-tab-btn");
            }
        }
        // Highlight the selected tab
        if (selected != null) {
            selected.getStyleClass().remove("course-page-tab-btn");
            selected.getStyleClass().add("course-page-tab-btn-selected");
        }
        activeTab = selected;
    }

    // ── Called from Scene1Controller when a course card is clicked ────
    public void setCourse(courseModel course) {
        this.course = course;

        if (course != null) {
            this.currentCourseId = course.getId();
        }
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

    // ── Load ALL resources once from API 
    private void loadResources() {
        notesContainer.getChildren().clear();

        // Show loading indicator
        Label loading = new Label("Loading resources...");
        loading.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        notesContainer.getChildren().add(loading);

        new Thread(() -> {
            try {
                courseResourceService serviceInstance = new courseResourceService();
                // Fetch all resources for this course
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

    // ── Filter and display resources by type 
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

    // ── Create a clickable resource card button 
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

        // Check if already downloaded — show green style immediately
        String resourceKey = course.getId() + "_" + resource.getId();
        File courseVaultFolder = getCourseVaultFolder();
        File existingFile = new File(courseVaultFolder, getSafeFileName(resource));

        if (existingFile.exists()
                || downloadedFiles.containsKey(resourceKey)) {
            card.getStyleClass().setAll("button", "course-page-resources-card-downloaded");
            card.setText("✅  " + resource.getTitle());
        } else {
            card.getStyleClass().setAll("button", "course-page-resources-card");
        }

        card.setOnMouseEntered(e -> {
            if (!card.getText().startsWith("✅")
                    && !card.getText().startsWith("⬇")) {
                card.getStyleClass().setAll("button", "course-page-resources-card-hover");
            }
        });
        card.setOnMouseExited(e -> {
            if (!card.getText().startsWith("✅")
                    && !card.getText().startsWith("⬇")) {
                card.getStyleClass().setAll("button", "course-page-resources-card");
            }
        });

        // Click handler
        card.setOnAction(e
                -> handleResourceClick(resource, card));

        return card;
    }

    // ── Smart click: open if downloaded, Save As if not 
    private void handleResourceClick(courseResourceModel resource, Button card) {

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
        downloadWithSaveDialog(resource, resourceKey, card);
    }

    private boolean isImageResource(
            courseResourceModel resource) {
        if (resource.getType() == null) {
            return false;
        }
        return resource.getType().equalsIgnoreCase("MIDTERM")
                || resource.getType().equalsIgnoreCase("FINAL");
    }
    // ── Save As dialog — like browser save image 

    private void downloadWithSaveDialog(courseResourceModel resource, String resourceKey, Button card) {

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
                String apiUrl = "http://localhost:8080/api/course-resources/" + resource.getId() + "/download";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(apiUrl)).GET().build();

                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                if (response.statusCode() == 200) {
                    Files.copy(response.body(), finalDest.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    // Cache so next click opens directly
                    downloadedFiles.put(resourceKey, finalDest);

                    Platform.runLater(() -> {
                        card.setDisable(false);
                        card.setText("✅ " + resource.getTitle());
                        card.getStyleClass().setAll("button", "course-page-resources-card-downloaded");
                        String fileName = finalDest.getName().toLowerCase();

                        if (mainController != null) {
                            mainController.incrementDownloadCount();
                        }
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
                    card.getStyleClass().setAll("button", "course-page-resources-card");
                    showErrorAlert("Download Failed",
                            "Could not download the file.\n" + e.getMessage());
                });
            }
        }).start();
    }

    // ── Open with system default app
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

    // ── Helpers 
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
        if (themeListener != null) {
            themeManager.removeListener(themeListener);
        }
        Stage stage = (Stage) courseRoot.getScene().getWindow();
        stage.close();
    }

    private Scene1Controller mainController;

    public void setMainController(Scene1Controller controller) {
        this.mainController = controller;
    }

    private void openImageViewer(File imageFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML1/ImageViewer.fxml"));
            Parent root = loader.load();
            ImageViewerController controller = loader.getController();

            // 1. Get the folder containing the clicked image
            File parentDir = imageFile.getParentFile();
            List<File> downloadedImages = new java.util.ArrayList<>();
            int selectedIndex = 0;

            if (parentDir != null && parentDir.isDirectory()) {
                // 2. Filter out only image files (.png, .jpg, .jpeg) in that folder
                File[] files = parentDir.listFiles((dir, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".pdf");
                });

                if (files != null) {
                    // Sort files alphabetically so page 1, page 2, etc., match the sequential order
                    java.util.Arrays.sort(files);

                    for (File file : files) {
                        downloadedImages.add(file);
                    }

                    // 3. Find the index of the clicked file in the list
                    selectedIndex = downloadedImages.indexOf(imageFile);
                    if (selectedIndex == -1) {
                        selectedIndex = 0; // Fallback if not found
                    }
                }
            }

            // If no companion files were found, at least pass the single clicked image
            if (downloadedImages.isEmpty()) {
                downloadedImages.add(imageFile);
                selectedIndex = 0;
            }

            // 4. Pass the list and correct index to the viewer controller
            controller.setImagesContext(downloadedImages, selectedIndex);

            Stage stage = new Stage();
            stage.setTitle(imageFile.getName());
            Scene scene = new Scene(root, 1000, 700);
            stage.setScene(scene);
            Image appIcon = new Image(getClass().getResourceAsStream("/Images/Logo.png"));
            stage.getIcons().add(appIcon);
            stage.initModality(javafx.stage.Modality.NONE);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private final externalLinkService externalService = new externalLinkService();
    private int currentCourseId;

    public void loadExternalLinksSection(int courseId) {
        notesContainer.getChildren().clear();

        List<externalLinkModel> links = externalService.fetchCourseLinks(courseId);

        if (links.isEmpty()) {
            Label noLinksLabel = new Label("No external links available.");
            noLinksLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-style: italic;");
            notesContainer.getChildren().add(noLinksLabel);
            return;
        }

        Map<String, List<externalLinkModel>> groupedLinks = links.stream()
                .collect(Collectors.groupingBy(
                        link -> link.getTopic() != null ? link.getTopic() : "General Resources"
                ));

        for (Map.Entry<String, List<externalLinkModel>> entry : groupedLinks.entrySet()) {
            String topicName = entry.getKey();
            List<externalLinkModel> topicLinks = entry.getValue();

            Label topicHeader = new Label(topicName.toUpperCase());
            topicHeader.getStyleClass().add("course-section-title");

            if (!notesContainer.getChildren().isEmpty()) {
                VBox.setMargin(topicHeader, new javafx.geometry.Insets(14, 0, 4, 0));
            } else {
                VBox.setMargin(topicHeader, new javafx.geometry.Insets(0, 0, 4, 0));
            }
            notesContainer.getChildren().add(topicHeader);

            for (externalLinkModel link : topicLinks) {
                Button linkBtn = new Button(link.getTitle());
                linkBtn.getStyleClass().add("filter-tab");
                linkBtn.setMaxWidth(Double.MAX_VALUE);
                linkBtn.setCursor(javafx.scene.Cursor.HAND);

                linkBtn.setOnAction(e -> {
                    try {
                        HostServices hostServices = (HostServices) linkBtn.getScene().getWindow().getProperties().get("hostServices");

                        if (hostServices != null) {
                            hostServices.showDocument(link.getUrl());
                        } else {
                            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                Desktop.getDesktop().browse(new URI(link.getUrl()));
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                notesContainer.getChildren().add(linkBtn);
            }
        }
    }

    @FXML
    private void toggleTheme(ActionEvent event) {
        themeManager.toggleTheme();
        applyCurrentTheme();
    }

    public void applyCurrentTheme() {
        if (courseRoot == null) {
            return;
        }
        Scene scene = courseRoot.getScene();
        if (scene == null) {
            return;
        }
        scene.getStylesheets().clear();
        if (!themeManager.isDarkMode()) {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/coursepagelightmode.css").toExternalForm());
            System.out.println("CoursePage: LIGHT MODE APPLIED");
        } else {
            scene.getStylesheets().add(getClass().getResource("/CSS/coursepage.css").toExternalForm());
            System.out.println("CoursePage: DARK MODE APPLIED");
        }
        scene.getRoot().applyCss();
        scene.getRoot().layout();
    }
    private Runnable themeListener;

    public Runnable getThemeListener() {
        return themeListener;
    }

    private void showAiSummary() {
        notesContainer.getChildren().clear();

        // ✅ Use cached result if already fetched — no repeat API calls
        if (cachedAiSummary != null) {
            displayAiSummary(cachedAiSummary);
            return;
        }

        // Loading state
        javafx.scene.layout.HBox loadingRow
                = new javafx.scene.layout.HBox(10);
        loadingRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        loadingRow.setStyle("-fx-padding: 16 0 0 0;");

        Label spinner = new Label("⟳");
        spinner.setStyle(
                "-fx-text-fill: #6366f1; -fx-font-size: 20px;");

        javafx.animation.RotateTransition rotate
                = new javafx.animation.RotateTransition(
                        javafx.util.Duration.millis(800), spinner);
        rotate.setByAngle(360);
        rotate.setCycleCount(javafx.animation.Animation.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();

        Label loadingText = new Label("Generating AI summary...");
        loadingText.setStyle(
                "-fx-text-fill: #64748b; -fx-font-size: 13px;");

        loadingRow.getChildren().addAll(spinner, loadingText);
        notesContainer.getChildren().add(loadingRow);

        // ✅ Call API on background thread
        new Thread(() -> {
            try {
                String url = "http://localhost:8080/api/courses/"
                        + course.getId() + "/ai-description";

                java.net.http.HttpClient client
                        = java.net.http.HttpClient.newHttpClient();

                // ✅ POST request — no body needed
                java.net.http.HttpRequest request
                        = java.net.http.HttpRequest.newBuilder()
                                .uri(java.net.URI.create(url))
                                .POST(java.net.http.HttpRequest.BodyPublishers.noBody())
                                .header("Content-Type", "application/json")
                                .build();

                java.net.http.HttpResponse<String> response
                        = client.send(request,
                                java.net.http.HttpResponse.BodyHandlers.ofString());

                System.out.println("AI Summary status: "
                        + response.statusCode());
                System.out.println("AI Summary body: " + response.body());

                if (response.statusCode() == 200) {
                    // ✅ Parse JSON response
                    com.fasterxml.jackson.databind.ObjectMapper mapper
                            = new com.fasterxml.jackson.databind.ObjectMapper();
                    com.fasterxml.jackson.databind.JsonNode root
                            = mapper.readTree(response.body());

                    // Get description field from JSON
                    String description = root.has("description")
                            ? root.get("description").asText()
                            : response.body();

                    // Cache it so clicking tab again doesn't re-fetch
                    cachedAiSummary = description;

                    Platform.runLater(() -> {
                        rotate.stop();
                        notesContainer.getChildren().clear();
                        displayAiSummary(description);
                    });

                } else {
                    throw new Exception("Server returned: "
                            + response.statusCode());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    rotate.stop();
                    notesContainer.getChildren().clear();

                    Label err = new Label(
                            "Failed to generate AI summary.\n"
                            + ex.getMessage());
                    err.setStyle(
                            "-fx-text-fill: #ef4444; -fx-font-size: 13px;");
                    err.setWrapText(true);
                    notesContainer.getChildren().add(err);
                });
            }
        }).start();
    }

// ── Render the AI summary text with markdown-like formatting 
    private void displayAiSummary(String text) {
        VBox summaryBox = new VBox(12);
        summaryBox.setStyle("-fx-padding: 8 0 0 0;");

        // AI badge header
        HBox aiHeader = new HBox(8);
        aiHeader.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label aiIcon = new Label("✨");
        aiIcon.setStyle("-fx-font-size: 16px;");

        Label aiLabel = new Label("AI Generated Summary");
        aiLabel.setStyle(
                "-fx-text-fill: #818cf8;"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;");

        Label aiDisclaimer = new Label("• May not be 100% accurate");
        aiDisclaimer.setStyle(
                "-fx-text-fill: #475569; -fx-font-size: 11px;");

        aiHeader.getChildren().addAll(aiIcon, aiLabel, aiDisclaimer);
        summaryBox.getChildren().add(aiHeader);

        //Parse and render each section
        String[] lines = text.split("\n");
        VBox currentSection = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("**") && line.endsWith("**")) {
                // ── Section heading 
                String heading = line.replace("**", "").trim();
                currentSection = new VBox(6);
                currentSection.setStyle(
                        "-fx-background-color: #1e293b;"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-color: #334155;"
                        + "-fx-border-radius: 10;"
                        + "-fx-padding: 14;");

                Label headingLabel = new Label(heading);
                headingLabel.setStyle(
                        "-fx-text-fill: #a5b4fc;"
                        + "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;");
                headingLabel.setWrapText(true);

                currentSection.getChildren().add(headingLabel);
                summaryBox.getChildren().add(currentSection);

            } else if (line.startsWith("-")) {
                // ── Bullet point 
                String bullet = line.substring(1).trim();
                // Clean up special chars from the API response
                bullet = bullet.replace("â", "—")
                        .replace("â¢", "•")
                        .replace("Bâ", "B-");

                HBox bulletRow = new HBox(8);
                bulletRow.setAlignment(javafx.geometry.Pos.TOP_LEFT);

                Label dot = new Label("•");
                dot.setStyle(
                        "-fx-text-fill: #6366f1;"
                        + "-fx-font-size: 13px;");
                dot.setMinWidth(12);

                Label bulletText = new Label(bullet);
                bulletText.setStyle(
                        "-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
                bulletText.setWrapText(true);
                HBox.setHgrow(bulletText,
                        javafx.scene.layout.Priority.ALWAYS);

                bulletRow.getChildren().addAll(dot, bulletText);

                if (currentSection != null) {
                    currentSection.getChildren().add(bulletRow);
                } else {
                    summaryBox.getChildren().add(bulletRow);
                }

            } else {
                // ── Regular paragraph text 
                String cleaned = line.replace("â", "—")
                        .replace("â¢", "•");

                Label paraLabel = new Label(cleaned);
                paraLabel.setStyle(
                        "-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
                paraLabel.setWrapText(true);

                if (currentSection != null) {
                    currentSection.getChildren().add(paraLabel);
                } else {
                    summaryBox.getChildren().add(paraLabel);
                }
            }
        }

        //Regenerate button — lets user refresh the AI summary
        Button regenerateBtn = new Button("🔄  Regenerate Summary");
        regenerateBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #64748b;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 8 16 8 16;"
                + "-fx-cursor: hand;");
        regenerateBtn.setOnMouseEntered(e -> regenerateBtn.setStyle(
                "-fx-background-color: #1e293b;"
                + "-fx-border-color: #6366f1;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #818cf8;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 8 16 8 16;"
                + "-fx-cursor: hand;"));
        regenerateBtn.setOnMouseExited(e -> regenerateBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #64748b;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 8 16 8 16;"
                + "-fx-cursor: hand;"));
        regenerateBtn.setOnAction(e -> {
            // Clear cache and re-fetch
            cachedAiSummary = null;
            showAiSummary();
        });

        summaryBox.getChildren().add(regenerateBtn);
        notesContainer.getChildren().add(summaryBox);
    }
}
