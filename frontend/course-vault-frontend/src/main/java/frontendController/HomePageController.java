/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontendController;

import com.mycompany.theme.themeManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Abreham
 */
public class HomePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
      @FXML
    private VBox cardAI;

    @FXML
    private VBox cardChoose;

    @FXML
    private VBox cardLinks;

    @FXML
    private VBox cardOpen;

    @FXML
    private VBox cardSpecific;

    @FXML
    private VBox cardTelegram;

    @FXML
    private VBox cardView;

    @FXML
    private VBox cardFind;

    @FXML
    private VBox cardQuickReview;

    @FXML
    private Button exploreCoursesBtn;

    @FXML
    private VBox homeRoot;

    private Scene1Controller mainController;
    private Runnable themeListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyCurrentTheme();

        if (exploreCoursesBtn != null) {
            exploreCoursesBtn.setOnAction(event -> handleExploreCourses());
        }
        Platform.runLater(() -> {
            if (cardTelegram != null) {
                setupCardHoverAnimation(cardTelegram);
            }
            if (cardLinks != null) {
                setupCardHoverAnimation(cardLinks);
            }
            if (cardAI != null) {
                setupCardHoverAnimation(cardAI);
            }
            if (cardSpecific != null) {
                setupCardHoverAnimation(cardSpecific);
            }
            if (cardChoose != null) {
                setupCardHoverAnimation(cardChoose);
            }
            if (cardOpen != null) {
                setupCardHoverAnimation(cardOpen);
            }
            if (cardView != null) {
                setupCardHoverAnimation(cardView);
            }
            if (cardFind != null) {
                setupCardHoverAnimation(cardFind);
            }
            if (cardQuickReview != null) {
                setupCardHoverAnimation(cardQuickReview);
            }
        });
        this.themeListener = () -> applyCurrentTheme();
        themeManager.addListener(themeListener);
    }

    public void setMainController(Scene1Controller controller) {
        this.mainController = controller;
    }

    @FXML
    private void handleExploreCourses() {
        if (mainController != null) {
            mainController.showCourse();
        }

    }

    public void applyCurrentTheme() {
        if (homeRoot == null) {
            return;
        }
        homeRoot.getStylesheets().clear();
        homeRoot.getStyleClass().removeAll("theme-dark", "theme-light");

        if (themeManager.isDarkMode()) {
            homeRoot.getStyleClass().add("theme-dark");
            homeRoot.getStylesheets().add(
                    getClass().getResource("/CSS/homepage.css").toExternalForm());
            System.out.println("HomePage: DARK MODE APPLIED LIVE");
        } else {
            homeRoot.getStyleClass().add("theme-light");
            homeRoot.getStylesheets().add(
                    getClass().getResource("/CSS/homepagelightmode.css").toExternalForm());
            System.out.println("HomePage: LIGHT MODE APPLIED LIVE");
        }
        updateCardStylesOnThemeChange();
    }

    private void updateCardStylesOnThemeChange() {
        String cardBackground = themeManager.isDarkMode() ? "#0f172a" : "#f8fafc";
        String cardBorder = themeManager.isDarkMode() ? "#1e293b" : "#e2e8f0";

        String finalStyle = "-fx-background-color: " + cardBackground + ";"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + cardBorder + ";"
                + "-fx-border-radius: 12;"
                + "-fx-border-width: 1;"
                + "-fx-padding: 20;"
                + "-fx-spacing: 12;"
                + "-fx-cursor: hand;";

        if (cardTelegram != null) {
            cardTelegram.setStyle(finalStyle);
        }
        if (cardLinks != null) {
            cardLinks.setStyle(finalStyle);
        }
        if (cardAI != null) {
            cardAI.setStyle(finalStyle);
        }
        if (cardSpecific != null) {
            cardSpecific.setStyle(finalStyle);
        }
        if (cardFind != null) {
            cardFind.setStyle(finalStyle);
        }
        if (cardOpen != null) {
            cardOpen.setStyle(finalStyle);
        }
        if (cardChoose != null) {
            cardChoose.setStyle(finalStyle);
        }
        if (cardView != null) {
            cardView.setStyle(finalStyle);
        }
        if (cardQuickReview != null) {
            cardQuickReview.setStyle(finalStyle);
        }
    }

    public Runnable getThemeListener() {
        return themeListener;
    }

    private void setupCardHoverAnimation(VBox card) {
        javafx.beans.property.DoubleProperty angle = new javafx.beans.property.SimpleDoubleProperty(0);

        javafx.animation.Timeline rotateGradient = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(16), e -> {
                    double a = angle.get();
                    double rad = Math.toRadians(a);

                    double x1 = 50 + Math.cos(rad) * 50;
                    double y1 = 50 + Math.sin(rad) * 50;
                    double x2 = 50 - Math.cos(rad) * 50;
                    double y2 = 50 - Math.sin(rad) * 50;

                    String borderColor1;
                    String borderColor2;

                    if (themeManager.isDarkMode()) {
                        borderColor1 = "rgba(255,255,255,0.4)";
                        borderColor2 = "rgba(56,189,248,1.0)";
                    } else {
                        borderColor1 = "rgba(0,0,0,0.2)";
                        borderColor2 = "rgba(2,132,199,1.0)";
                    }

                    String cardBackground = themeManager.isDarkMode() ? "#0f172a" : "#f8fafc";
                    card.setStyle(
                            "-fx-background-color: "
                            + "linear-gradient("
                            + "from " + x1 + "% " + y1 + "% "
                            + "to " + x2 + "% " + y2 + "%, "
                            + "rgba(255,255,255,0.0), "
                            + borderColor1 + ", "
                            + borderColor2 + ", "
                            + borderColor2 + ", "
                            + borderColor1 + ", "
                            + "rgba(255,255,255,0.0)"
                            + "), "
                            + cardBackground + ";"
                            + "-fx-background-insets: 0, 1.5;"
                            + "-fx-background-radius: 12, 11;"
                            + "-fx-padding: 20;"
                            + "-fx-spacing: 12;"
                            + "-fx-cursor: hand;"
                    );

                    angle.set((a + 3) % 360);
                })
        );
        rotateGradient.setCycleCount(javafx.animation.Animation.INDEFINITE);

        javafx.animation.ScaleTransition liftIn = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(200), card);
        liftIn.setToX(1.02);
        liftIn.setToY(1.02);

        javafx.animation.ScaleTransition liftOut = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(200), card);
        liftOut.setToX(1.0);
        liftOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            rotateGradient.play();
            liftIn.play();
        });

        card.setOnMouseExited(e -> {
            rotateGradient.stop();
            liftOut.play();

            String cardBackground = themeManager.isDarkMode() ? "#0f172a" : "#f8fafc";
            String cardBorder = themeManager.isDarkMode() ? "#1e293b" : "#e2e8f0";

            card.setStyle(
                    "-fx-background-color: " + cardBackground + ";"
                    + "-fx-background-radius: 12;"
                    + "-fx-border-color: " + cardBorder + ";"
                    + "-fx-border-radius: 12;"
                    + "-fx-border-width: 1;"
                    + "-fx-padding: 20;"
                    + "-fx-spacing: 12;"
                    + "-fx-cursor: hand;"
            );
        });
    }

}
