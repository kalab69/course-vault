/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontendController;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        });
        zoomOutBtn.setOnAction(e -> {
            zoomFactor -= 0.1;
            if (zoomFactor < 0.2) {
                zoomFactor = 0.2;
            }
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
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
        });

        imageView.setOnMousePressed(e -> {
            startX = e.getSceneX() - imageView.getTranslateX();
            startY = e.getSceneY() - imageView.getTranslateY();
        });
        imageView.setOnMouseDragged(e -> {
            imageView.setTranslateX(e.getSceneX() - startX);
            imageView.setTranslateY(e.getSceneY() - startY);
        });
    }

    public void loadImage(File imageFile) {
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
    }
}
