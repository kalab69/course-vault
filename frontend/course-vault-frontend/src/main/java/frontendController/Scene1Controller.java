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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Scene1Controller implements Initializable {

    // ── FXML fields ───────────────────────────────────────────────────────────
    @FXML private Button homeButton;
    @FXML private Button myCourseButton;
    @FXML private Button browseButton;
    @FXML private Button profileButton;
    @FXML private HBox navBar;
    @FXML private Rectangle navIndicator;
    @FXML private TextField appTitle;
    @FXML private BorderPane rootPane;

    // Sidebar
    @FXML private Button departmentMenuTrigger;
    @FXML private Button exammenuTrigger;
    @FXML private Button referencemenuTrigger;
    @FXML private VBox sidebarContainer;
    @FXML private Rectangle sidebarIndicator;
    @FXML private AnchorPane sidebarPane;
    @FXML private Button sidebarToggleBtn;
    @FXML private ImageView menuOpenIcon;
    @FXML private ImageView menuCloseIcon;

    // Welcome
    @FXML private Label welcomeLabel;
    @FXML private Label welcomeSub;

    // Stat cards
    @FXML private Label enrolledNum;
    @FXML private Label completedNum;
    @FXML private Label hoursNum;
    @FXML private VBox statCard1;
    @FXML private VBox statCard2;
    @FXML private VBox statCard3;

    // Progress bars
    @FXML private ProgressBar prog1;
    @FXML private ProgressBar prog2;
    @FXML private ProgressBar prog3;
    @FXML private Label prog1Label;
    @FXML private Label prog2Label;
    @FXML private Label prog3Label;

    // Continue learning cards
    @FXML private VBox courseCard1;
    @FXML private VBox courseCard2;
    @FXML private VBox courseCard3;

    // CS course cards
    @FXML private VBox csCard1;
    @FXML private VBox csCard2;
    @FXML private VBox csCard3;
    @FXML private VBox csCard4;
    @FXML private VBox csCard5;
    @FXML private VBox csCard6;
    @FXML private VBox csCard7;
    @FXML private VBox csCard8;
    @FXML private VBox csCard9;
    @FXML private VBox csCard10;
    @FXML private VBox csCard11;
    @FXML private VBox csCard12;

    // Accordion
    @FXML private VBox yearOneSection;
    @FXML private VBox yearTwoSection;
    @FXML private Button yearOneHeader;
    @FXML private Button yearTwoHeader;
    @FXML private ImageView yearOneArrow;
    @FXML private ImageView yearTwoArrow;
    
    @FXML private ScrollPane mainScrollPane;

    // ── Animation fields ──────────────────────────────────────────────────────
    private TranslateTransition slideTransition;
    private FadeTransition fadeTransition;
    private TranslateTransition verticalSlideTransition;
    private FadeTransition verticalFadeTransition;

    // ── Flyout / dropdown fields ───────────────────────────────────────────────
    private ContextMenu departmentsFlyout;
    private ContextMenu examFlyout;
    private ContextMenu referenceFlyout;
    private ContextMenu profileDropdown;

    // ── Sidebar toggle state ───────────────────────────────────────────────────
    private boolean sidebarOpen = true;
    private static final double SIDEBAR_WIDTH = 240.0;

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ════════════════════════════════════════════════
        // PART 1 — NAVBAR HORIZONTAL SLIDING INDICATOR
        // ════════════════════════════════════════════════
        slideTransition = new TranslateTransition(Duration.millis(300), navIndicator);
        fadeTransition  = new FadeTransition(Duration.millis(200), navIndicator);
        navIndicator.setOpacity(0.0);

        setupSlidingHover(homeButton);
        setupSlidingHover(myCourseButton);
        setupSlidingHover(browseButton);
        // profileButton handled in setupProfileDropdown()

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
        verticalFadeTransition  = new FadeTransition(Duration.millis(200), sidebarIndicator);
        sidebarIndicator.setOpacity(0.0);
        sidebarIndicator.toBack();

        // Build flyout menus FIRST so fields are never null
        setupCascadingSidebarMenu();

        // Profile dropdown
        setupProfileDropdown();

        // Mouse exit — only hide if not moving into a flyout
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

        // Snap indicator once layout is ready
        sidebarContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() ->
                    Platform.runLater(() ->
                        snapToSidebarButton(departmentMenuTrigger)
                    )
                );
            }
        });

        setupVerticalSlidingHover(departmentMenuTrigger);
        setupVerticalSlidingHover(exammenuTrigger);
        setupVerticalSlidingHover(referencemenuTrigger);


        // ════════════════════════════════════════════════
        // PART 3 — WELCOME & STATS
        // ════════════════════════════════════════════════
        welcomeLabel.setText("Welcome back, Abreham 👋");
        welcomeSub.setText("You have 3 courses in progress");

        enrolledNum.setText("12");
        completedNum.setText("5");
        hoursNum.setText("48h");

        setProgress(prog1, prog1Label, 0.72);
        setProgress(prog2, prog2Label, 0.38);
        setProgress(prog3, prog3Label, 0.15);


        // ════════════════════════════════════════════════
        // PART 4 — CARD HOVER ANIMATIONS
        // ════════════════════════════════════════════════
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
            setupCardHoverAnimation(csCard12);
        });


        // ════════════════════════════════════════════════
        // PART 5 — ACCORDION (starts collapsed)
        // ════════════════════════════════════════════════
        Platform.runLater(() -> {
            PauseTransition wait = new PauseTransition(Duration.millis(200));
            wait.setOnFinished(e -> {

                // yearOneSection children:
                //   [0] = Label "Computer Science"
                //   [1] = HBox (button + "Year I" label)
                //   [2] = HBox (csCard1, csCard2, csCard3)
                HBox yearOneRow =
                    (HBox) yearOneSection.getChildren().get(2);

                // yearTwoSection children:
                //   [0] = HBox (button + "Year II" label)
                //   [1] = HBox (csCard4-7)
                //   [2] = HBox (csCard8-10)
                //   [3] = HBox (csCard11-12)
                HBox yearTwoRow1 =
                    (HBox) yearTwoSection.getChildren().get(1);
                HBox yearTwoRow2 =
                    (HBox) yearTwoSection.getChildren().get(2);
                HBox yearTwoRow3 =
                    (HBox) yearTwoSection.getChildren().get(3);

                setupAccordion(yearOneHeader, yearOneArrow, yearOneRow);
                setupAccordion(yearTwoHeader, yearTwoArrow,
                    yearTwoRow1, yearTwoRow2, yearTwoRow3);

                // Collapse all on startup
                collapseImmediately(yearOneRow);
                collapseImmediately(yearTwoRow1);
                collapseImmediately(yearTwoRow2);
                collapseImmediately(yearTwoRow3);

                // Arrows point right = closed
                yearOneArrow.setRotate(0);
                yearTwoArrow.setRotate(0);
            });
            wait.play();
        });
        
        // Find the ScrollPane and make scrollbar fade in/out
Platform.runLater(() -> {
    // Get the scroll pane — add fx:id="mainScrollPane" to it in FXML
    // then inject it: @FXML private ScrollPane mainScrollPane;
    javafx.scene.control.ScrollPane sp = mainScrollPane;

    // Get the scrollbar node
    sp.skinProperty().addListener((obs, oldSkin, newSkin) -> {
        if (newSkin != null) {
            javafx.scene.Node vbar =
                sp.lookup(".scroll-bar:vertical");
            if (vbar != null) {
                vbar.setOpacity(0); // start hidden

                // Show on scroll
                sp.setOnScroll(e -> {
                    vbar.setOpacity(1.0);

                    // Fade out after 1.5 seconds of no scrolling
                    PauseTransition hide =
                        new PauseTransition(Duration.millis(1500));
                    hide.setOnFinished(ev -> {
                        FadeTransition fade =
                            new FadeTransition(Duration.millis(400), vbar);
                        fade.setFromValue(1.0);
                        fade.setToValue(0.0);
                        fade.play();
                    });
                    hide.play();
                });
            }
        }
    });
});
    }


    // ─────────────────────────────────────────────────────────────────────────
    // PROGRESS BAR HELPER
    // ─────────────────────────────────────────────────────────────────────────
    private void setProgress(ProgressBar bar, Label label, double value) {
        bar.setProgress(value);
        int percent = (int)(value * 100);
        label.setText(percent + "% complete");
    }


    // ─────────────────────────────────────────────────────────────────────────
    // NAVBAR METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupSlidingHover(Button btn) {
        btn.setOnMouseEntered(e -> {
            double targetX    = btn.getLayoutX();
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
        navIndicator.setOpacity(0.0);
    }


    // ─────────────────────────────────────────────────────────────────────────
    // SIDEBAR TOGGLE
    // ─────────────────────────────────────────────────────────────────────────
    @FXML
    public void toggleSidebar() {

        if (sidebarOpen) {
            // ── COLLAPSE ─────────────────────────────────────────────────
            javafx.animation.Timeline collapse = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(Duration.ZERO,
                    new javafx.animation.KeyValue(
                        sidebarPane.prefWidthProperty(),
                        SIDEBAR_WIDTH,
                        javafx.animation.Interpolator.EASE_IN),
                    new javafx.animation.KeyValue(
                        sidebarPane.opacityProperty(),
                        1.0,
                        javafx.animation.Interpolator.EASE_IN)
                ),
                new javafx.animation.KeyFrame(Duration.millis(300),
                    new javafx.animation.KeyValue(
                        sidebarPane.prefWidthProperty(),
                        0,
                        javafx.animation.Interpolator.EASE_IN),
                    new javafx.animation.KeyValue(
                        sidebarPane.opacityProperty(),
                        0.0,
                        javafx.animation.Interpolator.EASE_IN)
                )
            );

            collapse.setOnFinished(e -> {
                sidebarPane.setVisible(false);
                sidebarPane.setManaged(false);
                // Show close icon after sidebar hides
                swapIcon(false);
            });

            collapse.play();

        } else {
            // ── EXPAND ───────────────────────────────────────────────────
            sidebarPane.setVisible(true);
            sidebarPane.setManaged(true);
            sidebarPane.setPrefWidth(0);
            sidebarPane.setOpacity(0);

            // Show menu icon before sidebar expands
            swapIcon(true);

            javafx.animation.Timeline expand = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(Duration.ZERO,
                    new javafx.animation.KeyValue(
                        sidebarPane.prefWidthProperty(),
                        0,
                        javafx.animation.Interpolator.EASE_OUT),
                    new javafx.animation.KeyValue(
                        sidebarPane.opacityProperty(),
                        0.0,
                        javafx.animation.Interpolator.EASE_OUT)
                ),
                new javafx.animation.KeyFrame(Duration.millis(300),
                    new javafx.animation.KeyValue(
                        sidebarPane.prefWidthProperty(),
                        SIDEBAR_WIDTH,
                        javafx.animation.Interpolator.EASE_OUT),
                    new javafx.animation.KeyValue(
                        sidebarPane.opacityProperty(),
                        1.0,
                        javafx.animation.Interpolator.EASE_OUT)
                )
            );

            expand.setOnFinished(e ->
                sidebarPane.setPrefWidth(SIDEBAR_WIDTH));
            expand.play();
        }

        sidebarOpen = !sidebarOpen;
    }

    // showMenuIcon=true  → show hamburger (sidebar is open)
    // showMenuIcon=false → show close icon (sidebar is closed)
    private void swapIcon(boolean showMenuIcon) {
        ImageView fadeOutView = showMenuIcon ? menuCloseIcon : menuOpenIcon;
        ImageView fadeInView  = showMenuIcon ? menuOpenIcon  : menuCloseIcon;

        FadeTransition out = new FadeTransition(Duration.millis(150), fadeOutView);
        out.setFromValue(1.0);
        out.setToValue(0.0);
        out.setOnFinished(e -> {
            fadeOutView.setVisible(false);

            fadeInView.setVisible(true);
            fadeInView.setOpacity(0.0);

            FadeTransition in = new FadeTransition(Duration.millis(150), fadeInView);
            in.setFromValue(0.0);
            in.setToValue(1.0);
            in.play();
        });
        out.play();
    }


    // ─────────────────────────────────────────────────────────────────────────
    // SIDEBAR INDICATOR METHODS
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

            // Small delay prevents flickering when moving between buttons
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
        this.examFlyout        = new ContextMenu();
        this.referenceFlyout   = new ContextMenu();

        departmentsFlyout.getStyleClass().add("sidebar-flyout");
        examFlyout.getStyleClass().add("sidebar-flyout");
        referenceFlyout.getStyleClass().add("sidebar-flyout");

        departmentsFlyout.setAutoHide(true);
        examFlyout.setAutoHide(true);
        referenceFlyout.setAutoHide(true);

        fadeIndicatorWhenMouseEntersFlyout(departmentsFlyout);
        fadeIndicatorWhenMouseEntersFlyout(examFlyout);
        fadeIndicatorWhenMouseEntersFlyout(referenceFlyout);

        // ── Department menus ─────────────────────────────────────────────────
        Menu csDepartment = new Menu("Computer Science");
        Menu afDepartment = new Menu("Accounting and Finance");
        Menu mDepartment  = new Menu("Management");
        Menu eDepartment  = new Menu("Economics");
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

        // AF
        Menu afyear1 = new Menu("Year I"); Menu afyear2 = new Menu("Year II");
        Menu afyear3 = new Menu("Year III"); Menu afyear4 = new Menu("Year IV");
        afDepartment.getItems().addAll(afyear1, afyear2, afyear3, afyear4);

        // Management
        Menu myear1 = new Menu("Year I"); Menu myear2 = new Menu("Year II");
        Menu myear3 = new Menu("Year III"); Menu myear4 = new Menu("Year IV");
        mDepartment.getItems().addAll(myear1, myear2, myear3, myear4);

        // Economics
        Menu eyear1 = new Menu("Year I"); Menu eyear2 = new Menu("Year II");
        Menu eyear3 = new Menu("Year III"); Menu eyear4 = new Menu("Year IV");
        eDepartment.getItems().addAll(eyear1, eyear2, eyear3, eyear4);

        // Tourism
        Menu thyear1 = new Menu("Year I"); Menu thyear2 = new Menu("Year II");
        Menu thyear3 = new Menu("Year III"); Menu thyear4 = new Menu("Year IV");
        thDepartment.getItems().addAll(thyear1, thyear2, thyear3, thyear4);

        // Marketing
        Menu mmyear1 = new Menu("Year I"); Menu mmyear2 = new Menu("Year II");
        Menu mmyear3 = new Menu("Year III"); Menu mmyear4 = new Menu("Year IV");
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

        // Hide flyouts when hovering empty sidebar space
        sidebarContainer.setOnMouseEntered(e -> {
            if (e.getTarget() == sidebarContainer) {
                hideAllFlyouts(departmentsFlyout, examFlyout, referenceFlyout);
            }
        });
    }

    private void animateSubMenu(Menu menu) {
    // Only animate THIS menu's popup — do NOT recurse into children
    menu.setOnShowing(e -> {
        Platform.runLater(() -> {
            if (menu.getStyleableNode() != null) {
                javafx.scene.Node node = menu.getStyleableNode();
                javafx.scene.Parent parent = node.getParent();
                while (parent != null &&
                       !(parent instanceof javafx.scene.layout.Region)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    final javafx.scene.Parent content = parent;
                    content.setScaleY(0.0);
                    content.setOpacity(0.0);
                    content.setTranslateX(-10);

                    javafx.animation.Timeline slideIn =
                        new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(Duration.ZERO,
                                new javafx.animation.KeyValue(
                                    content.scaleYProperty(), 0.0,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    content.opacityProperty(), 0.0,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    content.translateXProperty(), -10.0,
                                    javafx.animation.Interpolator.EASE_OUT)
                            ),
                            new javafx.animation.KeyFrame(Duration.millis(180),
                                new javafx.animation.KeyValue(
                                    content.scaleYProperty(), 1.0,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    content.opacityProperty(), 1.0,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    content.translateXProperty(), 0.0,
                                    javafx.animation.Interpolator.EASE_OUT)
                            )
                        );
                    slideIn.play();
                }
            }
        });
    });
  
}

    private void fadeIndicatorWhenMouseEntersFlyout(ContextMenu flyout) {
        flyout.setOnShown(e -> {
            javafx.scene.Node content = flyout.getSkin().getNode();
            if (content != null) {

                // Slide down animation on flyout open
                content.setScaleY(0.0);
                content.setTranslateY(-content.getBoundsInLocal().getHeight() / 2);
                content.setOpacity(0.0);

                javafx.animation.Timeline slideDown = new javafx.animation.Timeline(
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

                // Fade sidebar indicator when mouse enters flyout
                content.setOnMouseEntered(ev -> {
                    verticalFadeTransition.stop();
                    verticalFadeTransition.setToValue(0.0);
                    verticalFadeTransition.play();
                });
            }
        });
    }

    private void hideAllFlyouts(ContextMenu... flyouts) {
        for (ContextMenu flyout : flyouts) {
            if (flyout != null && flyout.isShowing()) {
                javafx.scene.Node content = flyout.getSkin().getNode();
                if (content != null) {
                    javafx.animation.Timeline slideUp = new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(Duration.ZERO,
                            new javafx.animation.KeyValue(
                                content.scaleYProperty(), 1.0,
                                javafx.animation.Interpolator.EASE_IN),
                            new javafx.animation.KeyValue(
                                content.translateYProperty(), 0.0,
                                javafx.animation.Interpolator.EASE_IN),
                            new javafx.animation.KeyValue(
                                content.opacityProperty(), 1.0,
                                javafx.animation.Interpolator.EASE_IN)
                        ),
                        new javafx.animation.KeyFrame(Duration.millis(150),
                            new javafx.animation.KeyValue(
                                content.scaleYProperty(), 0.0,
                                javafx.animation.Interpolator.EASE_IN),
                            new javafx.animation.KeyValue(
                                content.translateYProperty(),
                                -content.getBoundsInLocal().getHeight() / 2,
                                javafx.animation.Interpolator.EASE_IN),
                            new javafx.animation.KeyValue(
                                content.opacityProperty(), 0.0,
                                javafx.animation.Interpolator.EASE_IN)
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

                if (screenX >= fx - buffer && screenX <= fx + fw + buffer
                        && screenY >= fy - buffer && screenY <= fy + fh + buffer) {
                    return true;
                }
            }
        }
        return false;
    }


    // ─────────────────────────────────────────────────────────────────────────
    // PROFILE DROPDOWN
    // ─────────────────────────────────────────────────────────────────────────
    private void setupProfileDropdown() {
        this.profileDropdown = new ContextMenu();
        profileDropdown.getStyleClass().add("sidebar-flyout");
        profileDropdown.setAutoHide(true);

        profileDropdown.setOnShown(e -> {
            javafx.scene.Node content = profileDropdown.getSkin().getNode();
            if (content != null) {

                content.setScaleY(0.0);
                content.setTranslateY(-content.getBoundsInLocal().getHeight() / 2);
                content.setOpacity(0.0);

                javafx.animation.Timeline slideDown = new javafx.animation.Timeline(
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
                    PauseTransition delay =
                        new PauseTransition(Duration.millis(100));
                    delay.setOnFinished(evv -> {
                        javafx.geometry.Bounds btnBounds =
                            profileButton.localToScreen(
                                profileButton.getBoundsInLocal());
                        if (btnBounds == null ||
                                !btnBounds.contains(mouseX, mouseY)) {
                            profileDropdown.hide();
                        }
                    });
                    delay.play();
                });
            }
        });

        MenuItem account  = new MenuItem("   👤  Account");
        MenuItem addFile  = new MenuItem("   📄  Add File");
        MenuItem settings = new MenuItem("   ⚙   Settings");
        MenuItem signOut  = new MenuItem("   🚪  Sign Out");
        signOut.setStyle("-fx-text-fill: #f87171;");

        account.setOnAction(e  -> System.out.println("Account"));
        addFile.setOnAction(e  -> System.out.println("Add File"));
        settings.setOnAction(e -> System.out.println("Settings"));
        signOut.setOnAction(e  -> System.out.println("Sign Out"));

        profileDropdown.getItems().addAll(
            account, addFile, settings,
            new javafx.scene.control.SeparatorMenuItem(), signOut);

        // Combined hover — sliding indicator + dropdown
        profileButton.setOnMouseEntered(e -> {
            double targetX     = profileButton.getLayoutX();
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
            PauseTransition delay =
                new PauseTransition(Duration.millis(100));
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
            double fx     = profileDropdown.getX();
            double fy     = profileDropdown.getY();
            double fw     = profileDropdown.getWidth();
            double fh     = profileDropdown.getHeight();
            double buffer = 10;

            return screenX >= fx - buffer && screenX <= fx + fw + buffer
                && screenY >= fy - buffer && screenY <= fy + fh + buffer;
        }
        return false;
    }


    // ─────────────────────────────────────────────────────────────────────────
    // CARD HOVER ANIMATION
    // ─────────────────────────────────────────────────────────────────────────
    private void setupCardHoverAnimation(VBox card) {

        javafx.beans.property.DoubleProperty angle =
            new javafx.beans.property.SimpleDoubleProperty(0);

        javafx.animation.Timeline rotateGradient = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(Duration.millis(16), e -> {
                double a   = angle.get();
                double rad = Math.toRadians(a);

                double x1 = 50 + Math.cos(rad) * 50;
                double y1 = 50 + Math.sin(rad) * 50;
                double x2 = 50 - Math.cos(rad) * 50;
                double y2 = 50 - Math.sin(rad) * 50;

                card.setStyle(
                    "-fx-background-color: " +
                    "linear-gradient(" +
                        "from " + x1 + "% " + y1 + "% " +
                        "to "  + x2 + "% " + y2 + "%, " +
                        "rgba(255,255,255,0.0), " +
                        "rgba(255,255,255,0.6), " +
                        "rgba(255,255,255,1.0), " +
                        "rgba(255,255,255,1.0), " +
                        "rgba(255,255,255,0.6), " +
                        "rgba(255,255,255,0.0)" +
                    "), " +
                    "#1e293b;" +
                    "-fx-background-insets: 0, 2.5;" +
                    "-fx-background-radius: 12, 10;" +
                    "-fx-padding: 16;" +
                    "-fx-spacing: 6;" +
                    "-fx-cursor: hand;"
                );

                angle.set((a + 3) % 360);
            })
        );
        rotateGradient.setCycleCount(javafx.animation.Animation.INDEFINITE);

        javafx.animation.ScaleTransition liftIn =
            new javafx.animation.ScaleTransition(Duration.millis(200), card);
        liftIn.setToX(1.02);
        liftIn.setToY(1.02);

        javafx.animation.ScaleTransition liftOut =
            new javafx.animation.ScaleTransition(Duration.millis(200), card);
        liftOut.setToX(1.0);
        liftOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            rotateGradient.play();
            liftIn.play();
        });

        card.setOnMouseExited(e -> {
            rotateGradient.stop();
            liftOut.play();
            card.setStyle(
                "-fx-background-color: #030202;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #334155;" +
                "-fx-border-radius: 12;" +
                "-fx-border-width: 1;" +
                "-fx-padding: 16;" +
                "-fx-spacing: 6;" +
                "-fx-cursor: hand;"
            );
        });
    }


    // ─────────────────────────────────────────────────────────────────────────
    // ACCORDION METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupAccordion(
            Button headerBtn,
            ImageView arrowImg,
            HBox... contentRows) {

        boolean[] isOpen = {false}; // starts CLOSED

        java.util.List<HBox> rows = java.util.Arrays.asList(contentRows);

        headerBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 0;"
        );

        arrowImg.setRotate(0); // pointing right = closed

        headerBtn.setOnMouseClicked(e -> {

            if (isOpen[0]) {
                // ── COLLAPSE ──────────────────────────────────────────────
                for (HBox row : rows) {
                    double startH = row.getHeight();

                    javafx.animation.Timeline collapse =
                        new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(Duration.ZERO,
                                new javafx.animation.KeyValue(
                                    row.maxHeightProperty(), startH,
                                    javafx.animation.Interpolator.EASE_IN),
                                new javafx.animation.KeyValue(
                                    row.opacityProperty(), 1.0,
                                    javafx.animation.Interpolator.EASE_IN)
                            ),
                            new javafx.animation.KeyFrame(Duration.millis(300),
                                new javafx.animation.KeyValue(
                                    row.maxHeightProperty(), 0,
                                    javafx.animation.Interpolator.EASE_IN),
                                new javafx.animation.KeyValue(
                                    row.opacityProperty(), 0.0,
                                    javafx.animation.Interpolator.EASE_IN)
                            )
                        );
                    collapse.setOnFinished(ev -> {
                        row.setVisible(false);
                        row.setManaged(false);
                    });
                    collapse.play();
                }

                javafx.animation.RotateTransition rotateClose =
                    new javafx.animation.RotateTransition(
                        Duration.millis(300), arrowImg);
                rotateClose.setFromAngle(90);
                rotateClose.setToAngle(0);
                rotateClose.play();

            } else {
                // ── EXPAND ────────────────────────────────────────────────
                for (HBox row : rows) {
                    row.setVisible(true);
                    row.setManaged(true);
                    row.setMaxHeight(0);
                    row.setOpacity(0);

                    row.applyCss();
                    row.layout();
                    double targetH = row.prefHeight(-1);

                    javafx.animation.Timeline expand =
                        new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(Duration.ZERO,
                                new javafx.animation.KeyValue(
                                    row.maxHeightProperty(), 0,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    row.opacityProperty(), 0.0,
                                    javafx.animation.Interpolator.EASE_OUT)
                            ),
                            new javafx.animation.KeyFrame(Duration.millis(300),
                                new javafx.animation.KeyValue(
                                    row.maxHeightProperty(), targetH,
                                    javafx.animation.Interpolator.EASE_OUT),
                                new javafx.animation.KeyValue(
                                    row.opacityProperty(), 1.0,
                                    javafx.animation.Interpolator.EASE_OUT)
                            )
                        );
                    expand.setOnFinished(ev ->
                        row.setMaxHeight(Double.MAX_VALUE));
                    expand.play();
                }

                javafx.animation.RotateTransition rotateOpen =
                    new javafx.animation.RotateTransition(
                        Duration.millis(300), arrowImg);
                rotateOpen.setFromAngle(0);
                rotateOpen.setToAngle(90);
                rotateOpen.play();
            }

            isOpen[0] = !isOpen[0];
        });
    }

    private void collapseImmediately(HBox row) {
        row.setVisible(false);
        row.setManaged(false);
        row.setMaxHeight(0);
        row.setOpacity(0);
    }
}