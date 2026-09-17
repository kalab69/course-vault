/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontendController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Abreham
 */
public class SplashController implements Initializable {

    @FXML
    private ImageView logoImage;
    @FXML
    private Label appTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String fullText = "CourseVault";
        Timeline typeWriter = new Timeline();

        for (int i = 0; i < fullText.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(150 * i),
                    event -> appTitle.setText(fullText.substring(0, index + 1)));
            typeWriter.getKeyFrames().add(keyFrame);
        }

        typeWriter.play();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), logoImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeTitle = new FadeTransition(Duration.seconds(3), appTitle);
        fadeTitle.setFromValue(0);
        fadeTitle.setToValue(1);
        fadeTitle.setAutoReverse(true);
        fadeTitle.setCycleCount(Animation.INDEFINITE);
        fadeTitle.play();

        RotateTransition rotate = new RotateTransition(Duration.seconds(15), logoImage);
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);

        ParallelTransition animation = new ParallelTransition(fadeIn, rotate);
        animation.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML1/Scene-1.fxml"));
                Stage stage = (Stage) appTitle.getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);

                stage.setMaximized(false); // reset first
                stage.setMaximized(true);  // then maximize again

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pause.play();

    }
}
