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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Scene1Controller implements Initializable {

    // ── FXML fields ───────────────────────────────────────────────────────────
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

    // ── Animation fields ──────────────────────────────────────────────────────
    private TranslateTransition slideTransition;
    private FadeTransition fadeTransition;
    private TranslateTransition verticalSlideTransition;
    private FadeTransition verticalFadeTransition;

    // ── Flyout fields (class-level so they are never null) ────────────────────
    private ContextMenu departmentsFlyout;
    private ContextMenu examFlyout;
    private ContextMenu referenceFlyout;
    private ContextMenu profileDropdown;

    // ─────────────────────────────────────────────────────────────────────────
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

        // Fade out indicator when mouse leaves the entire navbar
        navBar.setOnMouseExited(e -> {
            fadeTransition.stop();
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        });

        // Snap indicator to home button once scene + layout are ready
        homeButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> snapToButton(homeButton));
            }
        });

        // Ctrl+A → focus and select the search/title field
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

        // Request focus so shortcuts work immediately on launch
        Platform.runLater(() -> rootPane.requestFocus());

        // ════════════════════════════════════════════════
        // PART 2 — SIDEBAR VERTICAL SLIDING INDICATOR
        // ════════════════════════════════════════════════
        verticalSlideTransition = new TranslateTransition(Duration.millis(300), sidebarIndicator);
        verticalFadeTransition = new FadeTransition(Duration.millis(200), sidebarIndicator);
        sidebarIndicator.setOpacity(0.0);
        sidebarIndicator.toBack();

        // Step 1 — Build flyout menus FIRST so fields are never null
        setupCascadingSidebarMenu();
        setupProfileDropdown();

        // Step 2 — Mouse exit handler WITH flyout check so moving into
        //          a flyout does NOT close it. This is the ONLY place
        //          setOnMouseExited is set — setupCascadingSidebarMenu()
        //          does NOT set it, avoiding the overwrite bug.
        sidebarContainer.setOnMouseExited(e -> {
            double mouseX = e.getScreenX();
            double mouseY = e.getScreenY();

            // Only hide if mouse is NOT moving into an open flyout
            if (!isCursorOverFlyout(mouseX, mouseY)) {
                verticalFadeTransition.stop();
                verticalFadeTransition.setToValue(0.0);
                verticalFadeTransition.play();
                hideAllFlyouts(departmentsFlyout, examFlyout, referenceFlyout);
            }
        });

        // Step 3 — Snap indicator to first sidebar button after layout pass
        // Double runLater guarantees VBox sizing is fully computed
        sidebarContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(()
                        -> Platform.runLater(()
                                -> snapToSidebarButton(departmentMenuTrigger)
                        )
                );
            }
        });

        // Step 4 — Attach combined hover handler AFTER flyouts exist
        setupVerticalSlidingHover(departmentMenuTrigger);
        setupVerticalSlidingHover(exammenuTrigger);
        setupVerticalSlidingHover(referencemenuTrigger);
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

            // ── Sliding rectangle animation ──────────────────────────────────
            sidebarIndicator.setWidth(sidebarContainer.getWidth());
            sidebarIndicator.setHeight(btn.getHeight());
            sidebarIndicator.setLayoutX(0);
            sidebarIndicator.setLayoutY(0); // anchor to 0 — TranslateY does movement

            double targetY = btn.getBoundsInParent().getMinY();
            verticalSlideTransition.stop();
            verticalSlideTransition.setToY(targetY);
            verticalSlideTransition.play();

            verticalFadeTransition.stop();
            verticalFadeTransition.setToValue(1.0);
            verticalFadeTransition.play();

            sidebarIndicator.toBack(); // always behind button text

            // ── Flyout with small delay for smooth feel ──────────────────────
            // 80ms delay prevents the flyout flickering when sliding between buttons
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
        sidebarIndicator.setOpacity(0.0); // stays hidden until first hover
        sidebarIndicator.toBack();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FLYOUT / CONTEXT MENU METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupCascadingSidebarMenu() {

        // ── Create flyouts and assign to CLASS FIELDS (not local variables) ──
        this.departmentsFlyout = new ContextMenu();
        this.examFlyout = new ContextMenu();
        this.referenceFlyout = new ContextMenu();

        departmentsFlyout.getStyleClass().add("sidebar-flyout");
        examFlyout.getStyleClass().add("sidebar-flyout");
        referenceFlyout.getStyleClass().add("sidebar-flyout");

        // Auto-hide true by default but being explicit is safe
        departmentsFlyout.setAutoHide(true);
        examFlyout.setAutoHide(true);
        referenceFlyout.setAutoHide(true);

        fadeIndicatorWhenMouseEntersFlyout(departmentsFlyout);
        fadeIndicatorWhenMouseEntersFlyout(examFlyout);
        fadeIndicatorWhenMouseEntersFlyout(referenceFlyout);

        // ── Department menus ─────────────────────────────────────────────────
        Menu csDepartment = new Menu("Computer Science");
        Menu afDepartment = new Menu("Accounting and Finance");
        Menu mDepartment = new Menu("Management");
        Menu eDepartment = new Menu("Economics");
        Menu thDepartment = new Menu("Tourism and Hospitality Management");
        Menu mmDepartment = new Menu("Marketing Management");

        // CS Year submenus
        Menu csyear1 = new Menu("Year I");
        Menu csyear2 = new Menu("Year II");
        Menu csyear3 = new Menu("Year III");
        Menu csyear4 = new Menu("Year IV");
        csDepartment.getItems().addAll(csyear1, csyear2, csyear3, csyear4);

        // CS Year I courses
        csyear1.getItems().addAll(
                new MenuItem("Introduction to Computing Science (CoSc 1011)"),
                new MenuItem("Programming Fundamentals I (CoSc 1012)"),
                new MenuItem("Introduction to Emerging Technologies (EmTe 1012)")
        );

        // AF Year submenus
        Menu afyear1 = new Menu("Year I");
        Menu afyear2 = new Menu("Year II");
        Menu afyear3 = new Menu("Year III");
        Menu afyear4 = new Menu("Year IV");
        afDepartment.getItems().addAll(afyear1, afyear2, afyear3, afyear4);

        // Management Year submenus
        Menu myear1 = new Menu("Year I");
        Menu myear2 = new Menu("Year II");
        Menu myear3 = new Menu("Year III");
        Menu myear4 = new Menu("Year IV");
        mDepartment.getItems().addAll(myear1, myear2, myear3, myear4);

        // Economics Year submenus
        Menu eyear1 = new Menu("Year I");
        Menu eyear2 = new Menu("Year II");
        Menu eyear3 = new Menu("Year III");
        Menu eyear4 = new Menu("Year IV");
        eDepartment.getItems().addAll(eyear1, eyear2, eyear3, eyear4);

        // Tourism Year submenus
        Menu thyear1 = new Menu("Year I");
        Menu thyear2 = new Menu("Year II");
        Menu thyear3 = new Menu("Year III");
        Menu thyear4 = new Menu("Year IV");
        thDepartment.getItems().addAll(thyear1, thyear2, thyear3, thyear4);

        // Marketing Year submenus
        Menu mmyear1 = new Menu("Year I");
        Menu mmyear2 = new Menu("Year II");
        Menu mmyear3 = new Menu("Year III");
        Menu mmyear4 = new Menu("Year IV");
        mmDepartment.getItems().addAll(mmyear1, mmyear2, mmyear3, mmyear4);

        departmentsFlyout.getItems().addAll(
                csDepartment, afDepartment, mDepartment,
                eDepartment, thDepartment, mmDepartment
        );

        // ── Exam menus ───────────────────────────────────────────────────────
        examFlyout.getItems().addAll(
                new Menu("Computer Science"),
                new Menu("Accounting and Finance"),
                new Menu("Management"),
                new Menu("Economics"),
                new Menu("Tourism and Hospitality Management"),
                new Menu("Marketing Management")
        );

        // ── Reference menus ──────────────────────────────────────────────────
        referenceFlyout.getItems().addAll(
                new Menu("Computer Science"),
                new Menu("Accounting and Finance"),
                new Menu("Management"),
                new Menu("Economics"),
                new Menu("Tourism and Hospitality Management"),
                new Menu("Marketing Management")
        );

        // ── Sidebar background hover — hide flyouts when hovering empty space ─
        // NOTE: setOnMouseEntered for BUTTONS is handled in setupVerticalSlidingHover
        //       to avoid the overwrite bug. Only the container background is here.
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
                    // Slide up before hiding
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
                    // Hide the ContextMenu AFTER the animation finishes
                    slideUp.setOnFinished(ev -> flyout.hide());
                    slideUp.play();

                } else {
                    // Fallback if skin not ready
                    flyout.hide();
                }
            }
        }
    }

    /**
     * Returns true if the cursor screen position is within the bounds of any
     * currently visible flyout (plus a small buffer for the gap between the
     * sidebar edge and the flyout window).
     */
    private boolean isCursorOverFlyout(double screenX, double screenY) {
        ContextMenu[] flyouts = {departmentsFlyout, examFlyout, referenceFlyout};
        double buffer = 20; // px gap tolerance

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

                // ── Slide down animation on the flyout itself ────────────────
                content.setScaleY(0.0);         // start collapsed
                content.setTranslateY(-content.getBoundsInLocal().getHeight() / 2);
                // start from top edge
                content.setOpacity(0.0);        // start invisible

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

                // ── Fade indicator when mouse enters flyout ──────────────────
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

        // ── Slide down animation when shown ──────────────────────────────
        profileDropdown.setOnShown(e -> {
            javafx.scene.Node content = profileDropdown.getSkin().getNode();
            if (content != null) {

                // Slide animation
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

                // Hide when mouse leaves the dropdown
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

        // ── Menu items ────────────────────────────────────────────────────
        MenuItem account = new MenuItem("   👤  Account");
        MenuItem addFile = new MenuItem("   📄  Add File");
        MenuItem settings = new MenuItem("   ⚙   Settings");

        account.setOnAction(e -> System.out.println("Account"));
        addFile.setOnAction(e -> System.out.println("Add File"));
        settings.setOnAction(e -> System.out.println("Settings"));

        profileDropdown.getItems().addAll(
                account, addFile, settings);

        // ── COMBINED hover: sliding indicator + dropdown ──────────────────
        // NOTE: setupSlidingHover(profileButton) must be REMOVED
        //       from initialize() — this replaces it
        profileButton.setOnMouseEntered(e -> {

            // Sliding indicator — same logic as setupSlidingHover
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

            // Show dropdown below the button
            if (!profileDropdown.isShowing()) {
                profileDropdown.show(profileButton,
                        javafx.geometry.Side.BOTTOM, 0, 4);
            }
        });

        // Hide dropdown when mouse leaves button (if not entering dropdown)
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
}
