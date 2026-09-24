/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontendController;

import com.mycompany.model.courseModel;
import com.mycompany.model.courseResourceModel;
import com.mycompany.service.courseResourceService;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abreham
 */
public class CoursePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
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

    private courseModel course;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
notesBtn.setOnAction(event ->
    openPdfViewerFromResources("/PDFs/Operating Systems - Three Easy Pieces.pdf")
);
    }

    private void openPdfViewerFromResources(String resourcePath) {
    new Thread(() -> {
        try {
            // Extract resource to temp file
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }

            String fileName = resourcePath.substring(
                resourcePath.lastIndexOf("/") + 1);

            File tempFile = File.createTempFile("cv_", "_" + fileName);
            tempFile.deleteOnExit();
            Files.copy(is, tempFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
            is.close();

            System.out.println("✅ Temp file: " + tempFile.getAbsolutePath());

            // ✅ Open in system default app (Adobe, Edge, Firefox etc.)
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(tempFile);
                System.out.println("✅ Opened in default PDF viewer");
            } else {
                System.err.println("Desktop not supported on this system.");
            }

        } catch (Exception e) {
            System.err.println("Failed to open PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }).start();
}

//    private void launchPdfViewer(File pdfFile, String resourcePath) {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource("/FXML1/pdfViewer.fxml"));
//            Parent root = loader.load();
//
//            PdfViewerController controller = loader.getController();
//
//            Stage pdfStage = new Stage();
//            String fileName = resourcePath.substring(
//                    resourcePath.lastIndexOf("/") + 1);
//            pdfStage.setTitle(fileName);
//            pdfStage.initOwner(courseRoot.getScene().getWindow());
//
//            Scene scene = new Scene(root, 1000, 750);
//            pdfStage.setScene(scene);
//            pdfStage.setMaximized(true);
//
//            pdfStage.setOnHidden(e -> controller.cleanup());
//            pdfStage.show();
//
//            // File already extracted — pass directly, no re-extraction
//            Platform.runLater(() -> controller.openLocalFile(pdfFile));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

// ── Opens a PDF from a disk file path ────────────────────────────────
    private void openPdfViewer(File pdfFile) {
    new Thread(() -> {
        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(pdfFile);
                System.out.println("✅ Opened: " + pdfFile.getName());
            } else {
                System.err.println("Desktop not supported.");
            }
        } catch (Exception e) {
            System.err.println("Failed to open: " + e.getMessage());
            e.printStackTrace();
        }
    }).start();
}

    public void setCourse(courseModel course) {
        courseTitle.setText(course.getCourseName());
        courseCode.setText(course.getCode());

        // Match the tag color to the year
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
            }
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) courseRoot.getScene().getWindow();
        stage.close();
    }

    private void loadResources() {
        if (course == null) {
            return;
        }

        try {
            courseResourceService service = new courseResourceService();
            List<courseResourceModel> resources = service.fetchCourseResources(course.getId());

            // Safely clear old notes while keeping your master manual button intact
            notesContainer.getChildren().removeIf(node -> node != notesBtn);

            for (courseResourceModel resource : resources) {
                Button btn = new Button(resource.getTitle());
                btn.setStyle("-fx-background-color: #1f2937; -fx-text-fill: #f3f4f6; -fx-alignment: BASELINE_LEFT; -fx-max-width: Infinity; -fx-padding: 10 15; -fx-cursor: hand;");

                btn.setOnAction(event -> {
                    // Change getFilePath() to getUrl() to match your model's database pointer
                    String path = resource.getDownloadUrl();

                    if (path != null && !path.isEmpty()) {
                        File targetFile = new File(path);
                        if (targetFile.exists()) {
                            openPdfViewer(targetFile);
                        } else {
                            System.err.println("File does not exist on disk: " + path);
                        }
                    } else {
                        System.err.println("Resource path string is empty or null for: " + resource.getTitle());
                    }
                });

                notesContainer.getChildren().add(btn);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
