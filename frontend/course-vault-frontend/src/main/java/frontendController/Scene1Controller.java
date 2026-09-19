package frontendController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Scene1Controller implements Initializable {

    @FXML
    private Button homeButton;
    @FXML
    private Button myCourseButton;
    @FXML
    private Button browseButton;
    @FXML
    private Button profileButton;
    @FXML
    private HBox navBar;
    @FXML
    private Rectangle navIndicator;
    @FXML
    private TextField appTitle;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Button departmentMenuTrigger;
    @FXML
    private Button exammenuTrigger;
    @FXML
    private Button referencemenuTrigger;
    @FXML
    private VBox sidebarContainer;
    @FXML
    private Rectangle sidebarIndicator;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label welcomeSub;
    @FXML
    private Label enrolledNum;
    @FXML
    private Label completedNum;
    @FXML
    private Label hoursNum;
    @FXML
    private ProgressBar prog1;
    @FXML
    private ProgressBar prog2;
    @FXML
    private ProgressBar prog3;
    @FXML
    private Label prog1Label;
    @FXML
    private Label prog2Label;
    @FXML
    private Label prog3Label;
    @FXML
    private VBox statCard1;
    @FXML
    private VBox statCard2;
    @FXML
    private VBox statCard3;
    @FXML
    private VBox courseCard1;
    @FXML
    private VBox courseCard2;
    @FXML
    private VBox courseCard3;
    @FXML
    private VBox csCard1;
    @FXML
    private VBox csCard2;
    @FXML
    private VBox csCard3;
    @FXML
    private VBox csCard4;
    @FXML
    private VBox csCard5;
    @FXML
    private VBox csCard6;
    @FXML
    private VBox csCard7;
    @FXML
    private VBox csCard8;
    @FXML
    private VBox csCard9;
    @FXML
    private VBox csCard10;
    @FXML
    private VBox csCard11;

    private TranslateTransition slideTransition;
    private FadeTransition fadeTransition;
    private TranslateTransition verticalSlideTransition;
    private FadeTransition verticalFadeTransition;

    private ContextMenu departmentsFlyout;
    private ContextMenu examFlyout;
    private ContextMenu referenceFlyout;
    private ContextMenu profileDropdown;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ════════════════════════════════════════════════
        // PART 1 — NAVBAR HORIZONTAL SLIDING INDICATOR
        // ════════════════════════════════════════════════
        slideTransition = new TranslateTransition(Duration.millis(300), navIndicator);
        fadeTransition = new FadeTransition(Duration.millis(200), navIndicator);
        navIndicator.setOpacity(0.0);

        setupSlidingHover(homeButton);
        setupSlidingHover(myCourseButton);
        setupSlidingHover(browseButton);

        navBar.setOnMouseExited(e -> {
            fadeTransition.stop();
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        });

        homeButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> snapToButton(homeButton));
            }
        });

        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.isControlDown() && event.getCode() == KeyCode.A) {
                        appTitle.requestFocus();
                        appTitle.selectAll();
                        event.consume();
                    }
                });
            }
        });

        Platform.runLater(() -> rootPane.requestFocus());

        // ════════════════════════════════════════════════
        // PART 2 — SIDEBAR VERTICAL SLIDING INDICATOR
        // ════════════════════════════════════════════════
        verticalSlideTransition = new TranslateTransition(Duration.millis(300), sidebarIndicator);
        verticalFadeTransition = new FadeTransition(Duration.millis(200), sidebarIndicator);
        sidebarIndicator.setOpacity(0.0);
        sidebarIndicator.toBack();

        setupCascadingSidebarMenu();
        setupProfileDropdown();

        sidebarContainer.setOnMouseExited(e -> {
            double mouseX = e.getScreenX();
            double mouseY = e.getScreenY();

            if (!isCursorOverFlyout(mouseX, mouseY)) {
                verticalFadeTransition.stop();
                verticalFadeTransition.setToValue(0.0);
                verticalFadeTransition.play();
                hideAllFlyouts(departmentsFlyout, examFlyout, referenceFlyout);
            }
        });

        sidebarContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(()
                        -> Platform.runLater(()
                                -> snapToSidebarButton(departmentMenuTrigger)
                        )
                );
            }
        });

        setupVerticalSlidingHover(departmentMenuTrigger);
        setupVerticalSlidingHover(exammenuTrigger);
        setupVerticalSlidingHover(referencemenuTrigger);

        welcomeLabel.setText("Welcome back, _____");
        welcomeSub.setText("You have ___ courses in progress");

        enrolledNum.setText("___");
        completedNum.setText("___");
        hoursNum.setText("___h");

        setProgress(prog1, prog1Label, 0.72);  // 72%
        setProgress(prog2, prog2Label, 0.38);  // 38%
        setProgress(prog3, prog3Label, 0.15);  // 15%

        Platform.runLater(() -> {
            setupCardHoverAnimation(statCard1);
            setupCardHoverAnimation(statCard2);
            setupCardHoverAnimation(statCard3);
            setupCardHoverAnimation(courseCard1);
            setupCardHoverAnimation(courseCard2);
            setupCardHoverAnimation(courseCard3);
            setupCardHoverAnimation(csCard1);
            setupCardHoverAnimation(csCard2);
            setupCardHoverAnimation(csCard3);
            setupCardHoverAnimation(csCard4);
            setupCardHoverAnimation(csCard5);
            setupCardHoverAnimation(csCard6);
            setupCardHoverAnimation(csCard7);
            setupCardHoverAnimation(csCard8);
            setupCardHoverAnimation(csCard9);
            setupCardHoverAnimation(csCard10);
            setupCardHoverAnimation(csCard11);
        });
    }

    private void setProgress(ProgressBar bar, Label label, double value) {
        bar.setProgress(value);
        int percent = (int) (value * 100);
        label.setText(percent + "% complete");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // NAVBAR METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupSlidingHover(Button btn) {
        btn.setOnMouseEntered(e -> {
            double targetX = btn.getLayoutX();
            double tightHeight = btn.getHeight() - 6;

            navIndicator.setHeight(tightHeight);
            navIndicator.setWidth(btn.getWidth());
            navIndicator.setLayoutY(btn.getLayoutY() + 3);

            slideTransition.stop();
            slideTransition.setToX(targetX);
            slideTransition.play();

            fadeTransition.stop();
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        });
    }

    private void snapToButton(Button btn) {
        double tightHeight = btn.getHeight() - 6;
        navIndicator.setHeight(tightHeight);
        navIndicator.setWidth(btn.getWidth());
        navIndicator.setLayoutY(btn.getLayoutY() + 3);
        navIndicator.setTranslateX(btn.getLayoutX());
        navIndicator.setOpacity(0.0); // stays hidden until first hover
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SIDEBAR METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupVerticalSlidingHover(Button btn) {
        btn.setOnMouseEntered(e -> {

            sidebarIndicator.setWidth(sidebarContainer.getWidth());
            sidebarIndicator.setHeight(btn.getHeight());
            sidebarIndicator.setLayoutX(0);
            sidebarIndicator.setLayoutY(0);

            double targetY = btn.getBoundsInParent().getMinY();
            verticalSlideTransition.stop();
            verticalSlideTransition.setToY(targetY);
            verticalSlideTransition.play();

            verticalFadeTransition.stop();
            verticalFadeTransition.setToValue(1.0);
            verticalFadeTransition.play();

            sidebarIndicator.toBack();

            PauseTransition delay = new PauseTransition(Duration.millis(80));
            delay.setOnFinished(ev -> {
                hideAllFlyouts(departmentsFlyout, examFlyout, referenceFlyout);

                if (btn == departmentMenuTrigger && !departmentsFlyout.isShowing()) {
                    departmentsFlyout.show(btn, Side.RIGHT, 5, 0);
                } else if (btn == exammenuTrigger && !examFlyout.isShowing()) {
                    examFlyout.show(btn, Side.RIGHT, 5, 0);
                } else if (btn == referencemenuTrigger && !referenceFlyout.isShowing()) {
                    referenceFlyout.show(btn, Side.RIGHT, 5, 0);
                }
            });
            delay.play();
        });
    }

    private void snapToSidebarButton(Button btn) {
        sidebarIndicator.setWidth(sidebarContainer.getWidth());
        sidebarIndicator.setHeight(btn.getHeight());
        sidebarIndicator.setLayoutX(0);
        sidebarIndicator.setLayoutY(0);
        sidebarIndicator.setTranslateX(0);
        sidebarIndicator.setTranslateY(btn.getBoundsInParent().getMinY());
        sidebarIndicator.setOpacity(0.0);
        sidebarIndicator.toBack();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FLYOUT / CONTEXT MENU METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupCascadingSidebarMenu() {

        this.departmentsFlyout = new ContextMenu();
        this.examFlyout = new ContextMenu();
        this.referenceFlyout = new ContextMenu();

        departmentsFlyout.getStyleClass().add("sidebar-flyout");
        examFlyout.getStyleClass().add("sidebar-flyout");
        referenceFlyout.getStyleClass().add("sidebar-flyout");

        departmentsFlyout.setAutoHide(true);
        examFlyout.setAutoHide(true);
        referenceFlyout.setAutoHide(true);

        fadeIndicatorWhenMouseEntersFlyout(departmentsFlyout);
        fadeIndicatorWhenMouseEntersFlyout(examFlyout);
        fadeIndicatorWhenMouseEntersFlyout(referenceFlyout);

        Menu csDepartment = new Menu("Computer Science");
        Menu afDepartment = new Menu("Accounting and Finance");
        Menu mDepartment = new Menu("Management");
        Menu eDepartment = new Menu("Economics");
        Menu thDepartment = new Menu("Tourism and Hospitality Management");
        Menu mmDepartment = new Menu("Marketing Management");

        Menu csyear1 = new Menu("Year I");
        Menu csyear2 = new Menu("Year II");
        Menu csyear3 = new Menu("Year III");
        Menu csyear4 = new Menu("Year IV");
        csDepartment.getItems().addAll(csyear1, csyear2, csyear3, csyear4);

        csyear1.getItems().addAll(
                new MenuItem("Introduction to Computing Science (CoSc 1011)"),
                new MenuItem("Programming Fundamentals I (CoSc 1012)"),
                new MenuItem("Introduction to Emerging Technologies (EmTe 1012)")
        );

        Menu afyear1 = new Menu("Year I");
        Menu afyear2 = new Menu("Year II");
        Menu afyear3 = new Menu("Year III");
        Menu afyear4 = new Menu("Year IV");
        afDepartment.getItems().addAll(afyear1, afyear2, afyear3, afyear4);

        Menu myear1 = new Menu("Year I");
        Menu myear2 = new Menu("Year II");
        Menu myear3 = new Menu("Year III");
        Menu myear4 = new Menu("Year IV");
        mDepartment.getItems().addAll(myear1, myear2, myear3, myear4);

        Menu eyear1 = new Menu("Year I");
        Menu eyear2 = new Menu("Year II");
        Menu eyear3 = new Menu("Year III");
        Menu eyear4 = new Menu("Year IV");
        eDepartment.getItems().addAll(eyear1, eyear2, eyear3, eyear4);

        Menu thyear1 = new Menu("Year I");
        Menu thyear2 = new Menu("Year II");
        Menu thyear3 = new Menu("Year III");
        Menu thyear4 = new Menu("Year IV");
        thDepartment.getItems().addAll(thyear1, thyear2, thyear3, thyear4);

        Menu mmyear1 = new Menu("Year I");
        Menu mmyear2 = new Menu("Year II");
        Menu mmyear3 = new Menu("Year III");
        Menu mmyear4 = new Menu("Year IV");
        mmDepartment.getItems().addAll(mmyear1, mmyear2, mmyear3, mmyear4);

        departmentsFlyout.getItems().addAll(
                csDepartment, afDepartment, mDepartment,
                eDepartment, thDepartment, mmDepartment
        );

        examFlyout.getItems().addAll(
                new Menu("Computer Science"),
                new Menu("Accounting and Finance"),
                new Menu("Management"),
                new Menu("Economics"),
                new Menu("Tourism and Hospitality Management"),
                new Menu("Marketing Management")
        );

        referenceFlyout.getItems().addAll(
                new Menu("Computer Science"),
                new Menu("Accounting and Finance"),
                new Menu("Management"),
                new Menu("Economics"),
                new Menu("Tourism and Hospitality Management"),
                new Menu("Marketing Management")
        );

        sidebarContainer.setOnMouseEntered(e -> {
            if (e.getTarget() == sidebarContainer) {
                hideAllFlyouts(departmentsFlyout, examFlyout, referenceFlyout);
            }
        });
    }

    private void hideSidebarIndicatorOnly() {
    }

    private void hideAllFlyouts(ContextMenu... flyouts) {
        for (ContextMenu flyout : flyouts) {
            if (flyout != null && flyout.isShowing()) {

                javafx.scene.Node content = flyout.getSkin().getNode();
                if (content != null) {
                    javafx.animation.Timeline slideUp = new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(Duration.ZERO,
                                    new javafx.animation.KeyValue(content.scaleYProperty(), 1.0, javafx.animation.Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(content.translateYProperty(), 0.0, javafx.animation.Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(content.opacityProperty(), 1.0, javafx.animation.Interpolator.EASE_IN)
                            ),
                            new javafx.animation.KeyFrame(Duration.millis(150),
                                    new javafx.animation.KeyValue(content.scaleYProperty(), 0.0, javafx.animation.Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(content.translateYProperty(), -content.getBoundsInLocal().getHeight() / 2, javafx.animation.Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(content.opacityProperty(), 0.0, javafx.animation.Interpolator.EASE_IN)
                            )
                    );
                    slideUp.setOnFinished(ev -> flyout.hide());
                    slideUp.play();

                } else {
                    flyout.hide();
                }
            }
        }
    }

    private boolean isCursorOverFlyout(double screenX, double screenY) {
        ContextMenu[] flyouts = {departmentsFlyout, examFlyout, referenceFlyout};
        double buffer = 20;

        for (ContextMenu flyout : flyouts) {
            if (flyout != null && flyout.isShowing()) {
                double fx = flyout.getX();
                double fy = flyout.getY();
                double fw = flyout.getWidth();
                double fh = flyout.getHeight();

                if (screenX >= fx - buffer
                        && screenX <= fx + fw + buffer
                        && screenY >= fy - buffer
                        && screenY <= fy + fh + buffer) {
                    return true;
                }
            }
        }
        return false;
    }

    private void fadeIndicatorWhenMouseEntersFlyout(ContextMenu flyout) {
        flyout.setOnShown(e -> {
            javafx.scene.Node content = flyout.getSkin().getNode();
            if (content != null) {
                content.setScaleY(0.0);
                content.setTranslateY(-content.getBoundsInLocal().getHeight() / 2);
                content.setOpacity(0.0);

                javafx.animation.Timeline slideDown = new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(Duration.ZERO,
                                new javafx.animation.KeyValue(content.scaleYProperty(), 0.0, javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(content.translateYProperty(), -content.getBoundsInLocal().getHeight() / 2, javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(content.opacityProperty(), 0.0, javafx.animation.Interpolator.EASE_OUT)
                        ),
                        new javafx.animation.KeyFrame(Duration.millis(200),
                                new javafx.animation.KeyValue(content.scaleYProperty(), 1.0, javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(content.translateYProperty(), 0.0, javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(content.opacityProperty(), 1.0, javafx.animation.Interpolator.EASE_OUT)
                        )
                );
                slideDown.play();

                content.setOnMouseEntered(ev -> {
                    verticalFadeTransition.stop();
                    verticalFadeTransition.setToValue(0.0);
                    verticalFadeTransition.play();
                });
            }
        });
    }

    private void setupProfileDropdown() {
        this.profileDropdown = new ContextMenu();
        profileDropdown.getStyleClass().add("sidebar-flyout");
        profileDropdown.setAutoHide(true);

        profileDropdown.setOnShown(e -> {
            javafx.scene.Node content = profileDropdown.getSkin().getNode();
            if (content != null) {
                content.setScaleY(0.0);
                content.setTranslateY(
                        -content.getBoundsInLocal().getHeight() / 2);
                content.setOpacity(0.0);

                javafx.animation.Timeline slideDown
                        = new javafx.animation.Timeline(
                                new javafx.animation.KeyFrame(Duration.ZERO,
                                        new javafx.animation.KeyValue(
                                                content.scaleYProperty(), 0.0,
                                                javafx.animation.Interpolator.EASE_OUT),
                                        new javafx.animation.KeyValue(
                                                content.translateYProperty(),
                                                -content.getBoundsInLocal().getHeight() / 2,
                                                javafx.animation.Interpolator.EASE_OUT),
                                        new javafx.animation.KeyValue(
                                                content.opacityProperty(), 0.0,
                                                javafx.animation.Interpolator.EASE_OUT)
                                ),
                                new javafx.animation.KeyFrame(Duration.millis(200),
                                        new javafx.animation.KeyValue(
                                                content.scaleYProperty(), 1.0,
                                                javafx.animation.Interpolator.EASE_OUT),
                                        new javafx.animation.KeyValue(
                                                content.translateYProperty(), 0.0,
                                                javafx.animation.Interpolator.EASE_OUT),
                                        new javafx.animation.KeyValue(
                                                content.opacityProperty(), 1.0,
                                                javafx.animation.Interpolator.EASE_OUT)
                                )
                        );
                slideDown.play();

                content.setOnMouseExited(ev -> {
                    double mouseX = ev.getScreenX();
                    double mouseY = ev.getScreenY();

                    javafx.animation.PauseTransition delay
                            = new javafx.animation.PauseTransition(
                                    Duration.millis(100));
                    delay.setOnFinished(evv -> {
                        javafx.geometry.Bounds btnBounds
                                = profileButton.localToScreen(
                                        profileButton.getBoundsInLocal());
                        if (btnBounds == null
                                || !btnBounds.contains(mouseX, mouseY)) {
                            profileDropdown.hide();
                        }
                    });
                    delay.play();
                });
            }
        });

        MenuItem account = new MenuItem("   👤  Account");
        MenuItem addFile = new MenuItem("   📄  Add File");
        MenuItem settings = new MenuItem("   ⚙   Settings");

        account.setOnAction(e -> System.out.println("Account"));
        addFile.setOnAction(e -> System.out.println("Add File"));
        settings.setOnAction(e -> System.out.println("Settings"));

        profileDropdown.getItems().addAll(
                account, addFile, settings);

        profileButton.setOnMouseEntered(e -> {

            double targetX = profileButton.getLayoutX();
            double tightHeight = profileButton.getHeight() - 6;

            navIndicator.setHeight(tightHeight);
            navIndicator.setWidth(profileButton.getWidth());
            navIndicator.setLayoutY(profileButton.getLayoutY() + 3);

            slideTransition.stop();
            slideTransition.setToX(targetX);
            slideTransition.play();

            fadeTransition.stop();
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            if (!profileDropdown.isShowing()) {
                profileDropdown.show(profileButton,
                        javafx.geometry.Side.BOTTOM, 0, 4);
            }
        });

        profileButton.setOnMouseExited(e -> {
            double mouseX = e.getScreenX();
            double mouseY = e.getScreenY();

            javafx.animation.PauseTransition delay
                    = new javafx.animation.PauseTransition(Duration.millis(100));
            delay.setOnFinished(ev -> {
                if (!isMouseOverDropdown(mouseX, mouseY)) {
                    profileDropdown.hide();
                }
            });
            delay.play();
        });
    }

    private boolean isMouseOverDropdown(double screenX, double screenY) {
        if (profileDropdown != null && profileDropdown.isShowing()) {
            double fx = profileDropdown.getX();
            double fy = profileDropdown.getY();
            double fw = profileDropdown.getWidth();
            double fh = profileDropdown.getHeight();
            double buffer = 10;

            return screenX >= fx - buffer
                    && screenX <= fx + fw + buffer
                    && screenY >= fy - buffer
                    && screenY <= fy + fh + buffer;
        }
        return false;
    }

    private void setupCardHoverAnimation(javafx.scene.layout.VBox card) {

        javafx.beans.property.DoubleProperty angle
                = new javafx.beans.property.SimpleDoubleProperty(0);

        javafx.animation.Timeline rotateGradient
                = new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(Duration.millis(16), e -> {
                            double a = angle.get();
                            double rad = Math.toRadians(a);

                            // Spotlight position on the border
                            double x1 = 50 + Math.cos(rad) * 50;
                            double y1 = 50 + Math.sin(rad) * 50;
                            double x2 = 50 - Math.cos(rad) * 50;
                            double y2 = 50 - Math.sin(rad) * 50;

                            card.setStyle(
                                    "-fx-background-color: "
                                    + "linear-gradient("
                                    + "from " + x1 + "% " + y1 + "% "
                                    + "to " + x2 + "% " + y2 + "%, "
                                    + "rgba(0,255,255,0.0), "
                                    + "rgba(0,255,255,0.6), "
                                    + "rgba(0,255,255,1.0), "
                                    + "rgba(0,255,255,1.0), "
                                    + "rgba(0,255,255,0.6), "
                                    + "rgba(0,255,255,0.0)"
                                    + "), "
                                    + "#1e293b;"
                                    + "-fx-background-insets: 0, 1.5;"
                                    + "-fx-background-radius: 12, 11;"
                                    + "-fx-padding: 16;"
                                    + "-fx-spacing: 6;"
                                    + "-fx-cursor: hand;"
                            );

                            angle.set((a + 3) % 360); // speed — increase for faster
                        })
                );
        rotateGradient.setCycleCount(javafx.animation.Animation.INDEFINITE);

        // Lift effect on enter
        javafx.animation.ScaleTransition liftIn
                = new javafx.animation.ScaleTransition(Duration.millis(200), card);
        liftIn.setToX(1.02);
        liftIn.setToY(1.02);

        // Lower effect on exit
        javafx.animation.ScaleTransition liftOut
                = new javafx.animation.ScaleTransition(Duration.millis(200), card);
        liftOut.setToX(1.0);
        liftOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            rotateGradient.play();
            liftIn.play();
        });

        card.setOnMouseExited(e -> {
            rotateGradient.stop();
            liftOut.play();

            // Reset to normal static dark border
            card.setStyle(
                    "-fx-background-color: #05060A;"
                    + "-fx-background-radius: 12;"
                    + "-fx-border-color: #334155;"
                    + "-fx-border-radius: 12;"
                    + "-fx-border-width: 1;"
                    + "-fx-padding: 16;"
                    + "-fx-spacing: 6;"
                    + "-fx-cursor: hand;"
            );
        });
    }
}
