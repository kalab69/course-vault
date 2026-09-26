package frontendController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.model.courseModel;
import com.mycompany.model.courseResourceModel;
import com.mycompany.service.courseResourceService;
import com.mycompany.service.courseService;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import frontendController.ImageViewerController;
import java.awt.Desktop;

public class Scene1Controller implements Initializable {

    // ── FXML fields ───────────────────────────────────────────────────────────
    @FXML
    private TextField appTitle;

    @FXML
    private Button browseButton;

    @FXML
    private Label completedNum;

    @FXML
    private VBox courseContainer1;

    @FXML
    private VBox courseContainer2;

    @FXML
    private VBox courseContainer3;

    @FXML
    private VBox courseContainer4;

    @FXML
    private Button departmentMenuTrigger;

    @FXML
    private Label enrolledNum;

    @FXML
    private Button homeButton;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView menuCloseIcon;

    @FXML
    private ImageView menuOpenIcon;

    @FXML
    private Button myCourseButton;

    @FXML
    private HBox navBar;

    @FXML
    private Rectangle navIndicator;

    @FXML
    private BorderPane rootPane;

    @FXML
    private VBox sidebarContainer;

    @FXML
    private Rectangle sidebarIndicator;

    @FXML
    private AnchorPane sidebarPane;

    @FXML
    private Button sidebarToggleBtn;

    @FXML
    private VBox statCard1;

    @FXML
    private VBox statCard2;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label welcomeSub;

    @FXML
    private ImageView yearFourArrow;

    @FXML
    private Button yearFourHeader;

    @FXML
    private VBox yearFourSection;

    @FXML
    private ImageView yearOneArrow;

    @FXML
    private Button yearOneHeader;

    @FXML
    private VBox yearOneSection;

    @FXML
    private ImageView yearThreeArrow;

    @FXML
    private Button yearThreeHeader;

    @FXML
    private VBox yearThreeSection;

    @FXML
    private ImageView yearTwoArrow;

    @FXML
    private Button yearTwoHeader;

    @FXML
    private VBox yearTwoSection;

    private final courseService courseService = new courseService();

    // ── Animation fields ──────────────────────────────────────────────────────
    private TranslateTransition slideTransition;
    private FadeTransition fadeTransition;
    private TranslateTransition verticalSlideTransition;
    private FadeTransition verticalFadeTransition;

    // ── Flyout / dropdown fields ───────────────────────────────────────────────
    private ContextMenu departmentsFlyout;
    private ContextMenu examFlyout;
    private ContextMenu externalFlyout;
    private ContextMenu profileDropdown;

    // ── Sidebar toggle state ───────────────────────────────────────────────────
    private boolean sidebarOpen = true;
    private static final double SIDEBAR_WIDTH = 240.0;

    Node homeContent = null;

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

        Platform.runLater(() -> {
            snapToButton(homeButton);
            setActiveNavButton(homeButton);
        });

        homeButton.setOnAction(e -> {
            setActiveNavButton(homeButton);
        });
        myCourseButton.setOnAction(e -> {
            setActiveNavButton(myCourseButton);
        });
        browseButton.setOnAction(e -> {
            setActiveNavButton(browseButton);
        });

        myCourseButton.setOnAction(e -> showMyCourses());
        homeButton.setOnAction(e -> showHome());

        // ════════════════════════════════════════════════
        // PART 2 — SIDEBAR VERTICAL SLIDING INDICATOR
        // ════════════════════════════════════════════════
        verticalSlideTransition = new TranslateTransition(Duration.millis(300), sidebarIndicator);
        verticalFadeTransition = new FadeTransition(Duration.millis(200), sidebarIndicator);
        sidebarIndicator.setOpacity(0.0);
        sidebarIndicator.toBack();

        // Build flyout menus FIRST so fields are never null
        setupCascadingSidebarMenu();

        // Mouse exit — only hide if not moving into a flyout
        sidebarContainer.setOnMouseExited(e -> {
            double mouseX = e.getScreenX();
            double mouseY = e.getScreenY();
            if (!isCursorOverFlyout(mouseX, mouseY)) {
                verticalFadeTransition.stop();
                verticalFadeTransition.setToValue(0.0);
                verticalFadeTransition.play();
                hideAllFlyouts(departmentsFlyout, examFlyout, externalFlyout);
            }
        });

        // Snap indicator once layout is ready
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
        // ════════════════════════════════════════════════
        // PART 3 — WELCOME & STATS
        // ════════════════════════════════════════════════
        welcomeLabel.setText("Welcome");
        welcomeSub.setText("52 courses you have enrolled in");

        enrolledNum.setText("52");
        completedNum.setText(String.valueOf(countDownloadedFiles()));

        // ════════════════════════════════════════════════
        // PART 4 — CARD HOVER ANIMATIONS
        // ════════════════════════════════════════════════
        Platform.runLater(() -> {
            setupCardHoverAnimation(statCard1);
            setupCardHoverAnimation(statCard2);
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
                HBox yearOneRow
                        = (HBox) yearOneSection.getChildren().get(2);

                // yearTwoSection children:
                //   [0] = HBox (button + "Year II" label)
                //   [1] = HBox (csCard4-7)
                //   [2] = HBox (csCard8-10)
                //   [3] = HBox (csCard11-12)
                HBox yearTwoRow
                        = (HBox) yearTwoSection.getChildren().get(1);
                HBox yearThreeRow
                        = (HBox) yearThreeSection.getChildren().get(1);
                HBox yearFourRow
                        = (HBox) yearFourSection.getChildren().get(1);

                setupAccordion(yearOneHeader, yearOneArrow, yearOneRow);
                setupAccordion(yearTwoHeader, yearTwoArrow, yearTwoRow);
                setupAccordion(yearThreeHeader, yearThreeArrow, yearThreeRow);
                setupAccordion(yearFourHeader, yearFourArrow, yearFourRow);

                // Arrows point right = opened
                yearOneArrow.setRotate(90);
                yearTwoArrow.setRotate(90);
                yearThreeArrow.setRotate(90);
                yearFourArrow.setRotate(90);
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
                    javafx.scene.Node vbar
                            = sp.lookup(".scroll-bar:vertical");
                    if (vbar != null) {
                        vbar.setOpacity(0); // start hidden

                        // Show on scroll
                        sp.setOnScroll(e -> {
                            vbar.setOpacity(1.0);

                            // Fade out after 1.5 seconds of no scrolling
                            PauseTransition hide
                                    = new PauseTransition(Duration.millis(1500));
                            hide.setOnFinished(ev -> {
                                FadeTransition fade
                                        = new FadeTransition(Duration.millis(400), vbar);
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
        loadCourses();

        Platform.runLater(() -> {
            new Thread(() -> {
                populateDepartmentMenu();
            }).start();
        });
    }

    private void loadCourses() {
        // ✅ Load each year on background thread — never block JavaFX thread
        loadYearAsync("FIRST", courseContainer1);
        loadYearAsync("SECOND", courseContainer2);
        loadYearAsync("THIRD", courseContainer3);
        loadYearAsync("FOURTH", courseContainer4);
    }

    private void loadYearAsync(String year, VBox container) {
        new Thread(() -> {
            try {
                System.out.println("Fetching year: " + year);
                List<courseModel> courses
                        = courseService.fetchCoursesByYear(year);

                System.out.println("Got " + courses.size()
                        + " courses for year: " + year);

                // ✅ UI updates must be on JavaFX thread
                Platform.runLater(() -> {
                    container.getChildren().clear();

                    for (courseModel course : courses) {
                        VBox card = createCourseCard(course);
                        card.setOnMouseClicked(e -> openCourse(course));
                        container.getChildren().add(card);

                        // Apply hover animation after card is in scene
                        Platform.runLater(()
                                -> setupCardHoverAnimation(card));
                    }
                });

            } catch (IOException | InterruptedException ex) {
                System.err.println("Failed to fetch year "
                        + year + ": " + ex.getMessage());
                ex.printStackTrace();

                // ✅ Show error card in UI
                Platform.runLater(() -> {
                    Label errorLabel = new Label(
                            "Failed to load " + year + " courses");
                    errorLabel.setStyle(
                            "-fx-text-fill: #ef4444; -fx-font-size: 12px;");
                    container.getChildren().add(errorLabel);
                });
            }
        }).start();
    }

    private VBox createCourseCard(courseModel course) {

        VBox card = new VBox();
        card.setSpacing(6);

        // Course code tag/chip
        Label codeLabel = new Label(course.getCode());
        codeLabel.setStyle(getYearTagStyle(course.getYearLevel().name())
        );

        // Course name
        Label nameLabel = new Label(course.getCourseName());
        nameLabel.setStyle(
                "-fx-text-fill: #f1f5f9;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
                + "-fx-wrap-text: true;"
        );
        nameLabel.setWrapText(true);

        card.getChildren().addAll(codeLabel, nameLabel);

        // Base card style
        card.setStyle(
                "-fx-background-color: #030202;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 12;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 14;"
                + "-fx-cursor: hand;"
        );

        // ── Add the same spinning white border hover animation ────────────
        setupCardHoverAnimation(card);

        return card;
    }

    private int countDownloadedFiles() {
        File courseVaultFolder = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "CourseVault");
        if (!courseVaultFolder.exists()) {
            return 0;
        }
        File[] files
                = courseVaultFolder.listFiles();
        if (files == null) {
            return 0;
        }
        return files.length;
    }

    private String getYearTagStyle(String yearLevel) {
        if (yearLevel == null) {
            return "-fx-background-color: #1e293b;"
                    + "-fx-text-fill: #94a3b8;"
                    + "-fx-font-size: 11px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-background-radius: 6;"
                    + "-fx-padding: 3 10 3 10;";
        }

        switch (yearLevel) {
            case "FIRST":
                return // Purple — Year I
                        "-fx-background-color: #312e81;"
                        + "-fx-text-fill: #a5b4fc;"
                        + "-fx-font-size: 11px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 3 10 3 10;";

            case "SECOND":
                return // Green — Year II
                        "-fx-background-color: #14532d;"
                        + "-fx-text-fill: #86efac;"
                        + "-fx-font-size: 11px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 3 10 3 10;";

            case "THIRD":
                return // Orange — Year III
                        "-fx-background-color: #7c2d12;"
                        + "-fx-text-fill: #fdba74;"
                        + "-fx-font-size: 11px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 3 10 3 10;";

            case "FOURTH":
                return // Cyan — Year IV
                        "-fx-background-color: #164e63;"
                        + "-fx-text-fill: #67e8f9;"
                        + "-fx-font-size: 11px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 3 10 3 10;";

            default:
                return "-fx-background-color: #1e293b;"
                        + "-fx-text-fill: #94a3b8;"
                        + "-fx-font-size: 11px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 3 10 3 10;";
        }
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
        navIndicator.setOpacity(0.0);
    }

    private void setActiveNavButton(Button activeButton) {
        homeButton.getStyleClass().remove("nav-active");
        myCourseButton.getStyleClass().remove("nav-active");
        browseButton.getStyleClass().remove("nav-active");
        activeButton.getStyleClass().add("nav-active");
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

            expand.setOnFinished(e
                    -> sidebarPane.setPrefWidth(SIDEBAR_WIDTH));
            expand.play();
        }

        sidebarOpen = !sidebarOpen;
    }

    // showMenuIcon=true  → show hamburger (sidebar is open)
    // showMenuIcon=false → show close icon (sidebar is closed)
    private void swapIcon(boolean showMenuIcon) {
        ImageView fadeOutView = showMenuIcon ? menuCloseIcon : menuOpenIcon;
        ImageView fadeInView = showMenuIcon ? menuOpenIcon : menuCloseIcon;

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
                hideAllFlyouts(departmentsFlyout, examFlyout, externalFlyout);

                if (btn == departmentMenuTrigger && !departmentsFlyout.isShowing()) {
                    departmentsFlyout.show(btn, Side.RIGHT, 5, 0);
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
        this.externalFlyout = new ContextMenu();

        departmentsFlyout.getStyleClass().add("sidebar-flyout");
        examFlyout.getStyleClass().add("sidebar-flyout");
        externalFlyout.getStyleClass().add("sidebar-flyout");

        departmentsFlyout.setAutoHide(true);
        examFlyout.setAutoHide(true);
        externalFlyout.setAutoHide(true);

        fadeIndicatorWhenMouseEntersFlyout(departmentsFlyout);
        fadeIndicatorWhenMouseEntersFlyout(examFlyout);
        fadeIndicatorWhenMouseEntersFlyout(externalFlyout);

        // ── Year menus ─────────────────────────────────────────────────
        Menu csyear1 = new Menu("Year I");
        Menu csyear2 = new Menu("Year II");
        Menu csyear3 = new Menu("Year III");
        Menu csyear4 = new Menu("Year IV");

        departmentsFlyout.getItems().addAll(csyear1, csyear2, csyear3, csyear4);

        examFlyout.getItems().addAll(
                new Menu("Year I"),
                new Menu("Year II"),
                new Menu("Year III"),
                new Menu("Year IV")
        );

        externalFlyout.getItems().addAll(
                new Menu("Year I"),
                new Menu("Year II"),
                new Menu("Year III"),
                new Menu("Year IV")
        );

        // Hide flyouts when hovering empty sidebar space
        sidebarContainer.setOnMouseEntered(e -> {
            if (e.getTarget() == sidebarContainer) {
                hideAllFlyouts(departmentsFlyout, examFlyout, externalFlyout);
            }
        });
    }

    private void populateDepartmentMenu() {

        Menu year1 = new Menu("Year I");
        Menu year2 = new Menu("Year II");
        Menu year3 = new Menu("Year III");
        Menu year4 = new Menu("Year IV");

        try {

            addCoursesToMenu(
                    year1,
                    courseService.fetchCoursesByYear("FIRST"));

            addCoursesToMenu(
                    year2,
                    courseService.fetchCoursesByYear("SECOND"));

            addCoursesToMenu(
                    year3,
                    courseService.fetchCoursesByYear("THIRD"));

            addCoursesToMenu(
                    year4,
                    courseService.fetchCoursesByYear("FOURTH"));

            departmentsFlyout.getItems().setAll(
                    year1,
                    year2,
                    year3,
                    year4);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void addCoursesToMenu(
            Menu menu,
            List<courseModel> courses) {

        for (courseModel course : courses) {

            MenuItem item
                    = new MenuItem(
                            course.getCourseName());

            item.setOnAction(
                    e -> openCourse(course));

            menu.getItems().add(item);
        }
    }

    private void openCourse(courseModel course) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/FXML1/CoursePage.fxml"));
            Parent root = loader.load();

            CoursePageController controller = loader.getController();
            controller.setCourse(course);

            Stage courseStage = new Stage();
            courseStage.setTitle(course.getCourseName());
            courseStage.initModality(javafx.stage.Modality.NONE);
            courseStage.initOwner(rootPane.getScene().getWindow());

            courseStage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root, 900, 650);

            scene.setFill(Color.TRANSPARENT);

            courseStage.setScene(scene);
            courseStage.setResizable(true);

            courseStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public courseResourceModel getNotesPdf(
            int courseId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/resources/"
                + courseId + "/notes")).GET().build();
        HttpResponse<String> response
                = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
        ObjectMapper mapper
                = new ObjectMapper();
        return mapper.readValue(
                response.body(),
                courseResourceModel.class
        );
    }

    private void animateSubMenu(Menu menu) {
        // Only animate THIS menu's popup — do NOT recurse into children
        menu.setOnShowing(e -> {
            Platform.runLater(() -> {
                if (menu.getStyleableNode() != null) {
                    javafx.scene.Node node = menu.getStyleableNode();
                    javafx.scene.Parent parent = node.getParent();
                    while (parent != null
                            && !(parent instanceof javafx.scene.layout.Region)) {
                        parent = parent.getParent();
                    }
                    if (parent != null) {
                        final javafx.scene.Parent content = parent;
                        content.setScaleY(0.0);
                        content.setOpacity(0.0);
                        content.setTranslateX(-10);

                        javafx.animation.Timeline slideIn
                                = new javafx.animation.Timeline(
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
        ContextMenu[] flyouts = {departmentsFlyout, examFlyout, externalFlyout};
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
    // CARD HOVER ANIMATION
    // ─────────────────────────────────────────────────────────────────────────
    private void setupCardHoverAnimation(VBox card) {

        javafx.beans.property.DoubleProperty angle
                = new javafx.beans.property.SimpleDoubleProperty(0);

        javafx.animation.Timeline rotateGradient = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(Duration.millis(16), e -> {
                    double a = angle.get();
                    double rad = Math.toRadians(a);

                    double x1 = 50 + Math.cos(rad) * 50;
                    double y1 = 50 + Math.sin(rad) * 50;
                    double x2 = 50 - Math.cos(rad) * 50;
                    double y2 = 50 - Math.sin(rad) * 50;

                    card.setStyle(
                            "-fx-background-color: "
                            + "linear-gradient("
                            + "from " + x1 + "% " + y1 + "% "
                            + "to " + x2 + "% " + y2 + "%, "
                            + "rgba(255,255,255,0.0), "
                            + "rgba(255,255,255,0.6), "
                            + "rgba(255,255,255,1.0), "
                            + "rgba(255,255,255,1.0), "
                            + "rgba(255,255,255,0.6), "
                            + "rgba(255,255,255,0.0)"
                            + "), "
                            + "#1e293b;"
                            + "-fx-background-insets: 0, 2.5;"
                            + "-fx-background-radius: 12, 10;"
                            + "-fx-padding: 16;"
                            + "-fx-spacing: 6;"
                            + "-fx-cursor: hand;"
                    );

                    angle.set((a + 3) % 360);
                })
        );
        rotateGradient.setCycleCount(javafx.animation.Animation.INDEFINITE);

        javafx.animation.ScaleTransition liftIn
                = new javafx.animation.ScaleTransition(Duration.millis(200), card);
        liftIn.setToX(1.02);
        liftIn.setToY(1.02);

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
            card.setStyle(
                    "-fx-background-color: #030202;"
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

    // ─────────────────────────────────────────────────────────────────────────
    // ACCORDION METHODS
    // ─────────────────────────────────────────────────────────────────────────
    private void setupAccordion(
            Button headerBtn,
            ImageView arrowImg,
            HBox... contentRows) {

        boolean[] isOpen = {true};

        java.util.List<HBox> rows = java.util.Arrays.asList(contentRows);

        headerBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 0;"
        );

        arrowImg.setRotate(90);

        headerBtn.setOnMouseClicked(e -> {

            if (isOpen[0]) {
                // ── COLLAPSE ──────────────────────────────────────────────
                for (HBox row : rows) {
                    double startH = row.getHeight();

                    javafx.animation.Timeline collapse = new javafx.animation.Timeline(new javafx.animation.KeyFrame(Duration.ZERO,
                            new javafx.animation.KeyValue(row.maxHeightProperty(), startH, javafx.animation.Interpolator.EASE_IN),
                            new javafx.animation.KeyValue(row.opacityProperty(), 1.0, javafx.animation.Interpolator.EASE_IN)
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

                javafx.animation.RotateTransition rotateClose
                        = new javafx.animation.RotateTransition(
                                Duration.millis(300), arrowImg);
                rotateClose.setFromAngle(90);
                rotateClose.setToAngle(0);
                rotateClose.play();

            } else {
                // ── EXPAND ────────────────────────────────────────────────
                for (HBox row : rows) {
                    row.setVisible(true);
                    row.setManaged(true);
                    row.setMaxHeight(Double.MAX_VALUE);
                    row.setOpacity(1.0);

                    row.applyCss();
                    row.layout();
                    double targetH = row.prefHeight(-1);

                    javafx.animation.Timeline expand
                            = new javafx.animation.Timeline(
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
                    expand.setOnFinished(ev
                            -> row.setMaxHeight(Double.MAX_VALUE));
                    expand.play();
                }

                javafx.animation.RotateTransition rotateOpen
                        = new javafx.animation.RotateTransition(
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

    private void showHome() {
        if (homeContent != null) {
            rootPane.setLeft(sidebarPane);
            sidebarPane.setVisible(true);
            sidebarPane.setManaged(true);

            rootPane.setCenter(homeContent);
        }
        setNavActive(homeButton);
    }

    private void showMyCourses() {
        if (homeContent == null) {
            homeContent = rootPane.getCenter();
        }

        rootPane.setLeft(null);

        setNavActive(myCourseButton);

        VBox myCoursesContent = buildMyCoursesView();

        ScrollPane sp = new ScrollPane(myCoursesContent);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-background: transparent;");

        rootPane.setCenter(sp);
    }

    // Highlight active nav button
    private void setNavActive(Button active) {
        Button[] navBtns = {homeButton, myCourseButton, browseButton};

        for (Button btn : navBtns) {
            if (btn != null) {
                btn.getStyleClass().remove("nav-active");
            }
        }

        if (active != null) {
            active.getStyleClass().add("nav-active");
            Platform.runLater(() -> snapToButton(active));
        }
    }

    private VBox buildMyCoursesView() {
        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 28; -fx-background-color: #0a0a0a;");

        // ── Header ───────────────────────────────────────────────────────
        Label title = new Label("My Resources");
        title.setStyle(
                "-fx-text-fill: #f1f5f9;"
                + "-fx-font-size: 22px;"
                + "-fx-font-weight: bold;");

        Label sub = new Label("Files you have downloaded");
        sub.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");

        VBox header = new VBox(4, title, sub);
        root.getChildren().add(header);

        File courseVaultFolder = new File(
                System.getProperty("user.home")
                + File.separator + "Downloads"
                + File.separator + "CourseVault");

        if (!courseVaultFolder.exists() || courseVaultFolder.listFiles() == null) {
            Label empty = new Label("No downloaded resources yet.\nOpen a course and download files to see them here.");
            empty.setWrapText(true);
            empty.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");
            root.getChildren().add(empty);
            return root;
        }

        File[] files = courseVaultFolder.listFiles();
        if (files == null || files.length == 0) {
            Label empty = new Label("No downloaded resources yet.");
            empty.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");
            root.getChildren().add(empty);
            return root;
        }

        Label countLabel = new Label(files.length + " file"
                + (files.length != 1 ? "s" : "") + " downloaded");
        countLabel.setStyle(
                "-fx-text-fill: #6366f1;"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;");
        root.getChildren().add(countLabel);

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }

            HBox card = buildFileCard(file);
            root.getChildren().add(card);
        }

        return root;
    }

    private HBox buildFileCard(File file) {
        HBox card = new javafx.scene.layout.HBox(14);
        card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Choose icon based on extension
        String name = file.getName().toLowerCase();
        String icon = name.endsWith(".pdf") ? "📄"
                : name.endsWith(".jpg")
                || name.endsWith(".jpeg")
                || name.endsWith(".png") ? "🖼" : "📁";

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 22px;");

        // File info
        VBox info = new VBox(4);
        Label nameLabel = new Label(file.getName());
        nameLabel.setStyle(
                "-fx-text-fill: #f1f5f9;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;");

        long sizeKb = file.length() / 1024;
        String sizeStr = sizeKb > 1024
                ? String.format("%.1f MB", sizeKb / 1024.0)
                : sizeKb + " KB";

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("MMM dd, yyyy");
        String dateStr = sdf.format(
                new java.util.Date(file.lastModified()));

        Label meta = new Label(sizeStr + "  ·  " + dateStr);
        meta.setStyle("-fx-text-fill: #475569; -fx-font-size: 11px;");

        info.getChildren().addAll(nameLabel, meta);

        Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button openBtn = new Button("Open");
        openBtn.setStyle(
                "-fx-background-color: #1e293b;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #94a3b8;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 6 14 6 14;"
                + "-fx-cursor: hand;");
        openBtn.setOnMouseEntered(e -> openBtn.setStyle(
                "-fx-background-color: #6366f1;"
                + "-fx-border-color: #6366f1;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 6 14 6 14;"
                + "-fx-cursor: hand;"));
        openBtn.setOnMouseExited(e -> openBtn.setStyle(
                "-fx-background-color: #1e293b;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #94a3b8;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 6 14 6 14;"
                + "-fx-cursor: hand;"));
        openBtn.setOnAction(e -> openDownloadedFile(file));

        Button deleteBtn = new Button("🗑");
        deleteBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-text-fill: #475569;"
                + "-fx-font-size: 14px;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 4 8 4 8;");
        deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(
                "-fx-background-color: #7f1d1d;"
                + "-fx-border-color: transparent;"
                + "-fx-background-radius: 6;"
                + "-fx-text-fill: #fca5a5;"
                + "-fx-font-size: 14px;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 4 8 4 8;"));
        deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-text-fill: #475569;"
                + "-fx-font-size: 14px;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 4 8 4 8;"));
        deleteBtn.setOnAction(e -> {
            file.delete();

            showMyCourses();
        });

        card.getChildren().addAll(iconLabel, info, spacer, openBtn, deleteBtn);
        card.setStyle(
                "-fx-background-color: #1e293b;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 12;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 14 16 14 16;");

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #1a2540;"
                + "-fx-border-color: #6366f1;"
                + "-fx-border-radius: 12;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 14 16 14 16;"));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #1e293b;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 12;"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 14 16 14 16;"));

        return card;
    }

    private boolean isActualImageFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        String name = file.getName().toLowerCase();

        if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".webp")) {
            return true;
        }

        if (name.endsWith(".pdf")) {
            try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
                byte[] header = new byte[4];
                int bytesRead = fis.read(header);

                if (bytesRead >= 2) {
                    if ((header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8) {
                        return true;
                    }
                    if (bytesRead >= 4 && (header[0] & 0xFF) == 0x89 && (header[1] & 0xFF) == 0x50
                            && (header[2] & 0xFF) == 0x4E && (header[3] & 0xFF) == 0x47) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return name.contains("mid") || name.contains("final");
            }
        }
        return false;
    }

    private void openDownloadedFile(File file) {
        if (isActualImageFile(file)) {
            Platform.runLater(() -> openImageViewer(file));
        } else {
            new Thread(() -> {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    private void openImageViewer(File imageFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML1/ImageViewer.fxml"));
            Parent root = loader.load();
            ImageViewerController controller = loader.getController();

            File parentDir = imageFile.getParentFile();
            List<File> downloadedImages = new java.util.ArrayList<>();
            int selectedIndex = 0;

            if (parentDir != null && parentDir.isDirectory()) {
                File[] files = parentDir.listFiles((dir, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".webp");
                });

                if (files != null) {
                    java.util.Arrays.sort(files);
                    for (File file : files) {
                        downloadedImages.add(file);
                    }
                    selectedIndex = downloadedImages.indexOf(imageFile);
                    if (selectedIndex == -1) {
                        selectedIndex = 0;
                    }
                }
            }

            if (downloadedImages.isEmpty()) {
                downloadedImages.add(imageFile);
                selectedIndex = 0;
            }

            controller.setImagesContext(downloadedImages, selectedIndex);

            Stage stage = new Stage();
            stage.setTitle(imageFile.getName());
            Scene scene = new Scene(root, 1000, 700);
            stage.setScene(scene);

            try {
                Image appIcon = new Image(getClass().getResourceAsStream("/Images/Logo.png"));
                stage.getIcons().add(appIcon);
            } catch (Exception e) {
                System.out.println("Logo missing: " + e.getMessage());
            }

            stage.initModality(javafx.stage.Modality.NONE);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
