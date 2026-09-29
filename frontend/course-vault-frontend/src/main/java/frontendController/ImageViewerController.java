/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontendController;

import com.mycompany.theme.themeManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abreham
 */
public class ImageViewerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button backBtn;

    @FXML
    private ImageView imageView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button zoomInBtn;

    @FXML
    private Button zoomOutBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button prevBtn;

    @FXML
    private BorderPane imageViewPane;

    private double zoomFactor = 1.0;
    private double startX;
    private double startY;

    private List<File> images;
    private int currentIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zoomInBtn.setOnAction(e -> {
            zoomFactor += 0.1;
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
            updateZoom();
        });
        zoomOutBtn.setOnAction(e -> {
            zoomFactor -= 0.1;
            if (zoomFactor < 0.2) {
                zoomFactor = 0.2;
            }
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
            updateZoom();
        });
        backBtn.setOnAction(e -> {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
        });

        imageView.setOnScroll(event -> {
            double delta = event.getDeltaY();
            if (delta > 0) {
                zoomFactor *= 1.1;
            } else {
                zoomFactor /= 1.1;
            }
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);

            updateZoom();
        });

        imageView.setOnMousePressed(e -> {
            startX = e.getSceneX() - imageView.getTranslateX();
            startY = e.getSceneY() - imageView.getTranslateY();
        });
        imageView.setOnMouseDragged(e -> {
            imageView.setTranslateX(e.getSceneX() - startX);
            imageView.setTranslateY(e.getSceneY() - startY);
        });

        nextBtn.setOnAction(e -> {
            if (images != null && currentIndex < images.size() - 1) {
                currentIndex++;
                displayCurrentImage();
            }
        });
        prevBtn.setOnAction(e -> {
            if (images != null && currentIndex > 0) {
                currentIndex--;
                displayCurrentImage();
            }
        });

        scrollPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
                    switch (event.getCode()) {
                        case RIGHT:
                            nextBtn.fire();
                            event.consume();
                            break;
                        case LEFT:
                            prevBtn.fire();
                            event.consume();
                            break;
                        case PLUS:
                        case EQUALS:
                            zoomInBtn.fire();
                            event.consume();
                            break;
                        case MINUS:
                        case SUBTRACT:
                            zoomOutBtn.fire();
                            event.consume();
                            break;
                        default:
                            break;
                    }
                });
            }
        });

        imageViewPane.sceneProperty().addListener(
                (obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        applyCurrentTheme();
                    }
                });
    }

    private void updateZoom() {
        imageView.setScaleX(zoomFactor);
        imageView.setScaleY(zoomFactor);
    }

    public void setImagesContext(List<File> imageList, int initialIndex) {
        this.images = imageList;
        this.currentIndex = initialIndex;
        displayCurrentImage();
    }

    private void displayCurrentImage() {
        if (images != null && !images.isEmpty() && currentIndex >= 0 && currentIndex < images.size()) {
            File currentFile = images.get(currentIndex);
            Image image = new Image(currentFile.toURI().toString());
            imageView.setImage(image);

            zoomFactor = 1.0;
            updateZoom();
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);

            prevBtn.setDisable(currentIndex == 0);
            nextBtn.setDisable(currentIndex == images.size() - 1);
        }
    }

    @FXML
    private void toggleTheme(ActionEvent event) {
        themeManager.toggleTheme();
        applyCurrentTheme();
    }

    public void applyCurrentTheme() {
        if (imageViewPane == null) {
            return;
        }
        Scene scene = imageViewPane.getScene();
        if (scene == null) {
            return;
        }
        scene.getStylesheets().clear();
        if (!themeManager.isDarkMode()) {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/imageviewerlightmode.css").toExternalForm());
            System.out.println("CoursePage: LIGHT MODE APPLIED");
        } else {
            scene.getStylesheets().add(getClass().getResource("/CSS/imageviewer.css").toExternalForm());
            System.out.println("CoursePage: DARK MODE APPLIED");
        }
        scene.getRoot().applyCss();
        scene.getRoot().layout();
    }
}
