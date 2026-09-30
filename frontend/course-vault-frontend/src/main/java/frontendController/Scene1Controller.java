package frontendController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.model.courseModel;
import com.mycompany.model.courseResourceModel;
import com.mycompany.service.courseResourceService;
import com.mycompany.service.courseService;
import com.mycompany.theme.themeManager;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.awt.Desktop;
import static java.net.URLEncoder.encode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;

public class Scene1Controller implements Initializable {

    // ── FXML fields 
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
    private Button themeToggleBtn;

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

    // ── Animation fields 
    private TranslateTransition slideTransition;
    private FadeTransition fadeTransition;
    private TranslateTransition verticalSlideTransition;
    private FadeTransition verticalFadeTransition;

    // ── Flyout / dropdown fields 
    private ContextMenu departmentsFlyout;
    private ContextMenu examFlyout;
    private ContextMenu externalFlyout;
    private ContextMenu profileDropdown;

    // ── Sidebar toggle state 
    private boolean sidebarOpen = true;
    private static final double SIDEBAR_WIDTH = 240.0;

    Node homeContent = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // --------------------------------------
        // PART 1 — NAVBAR HORIZONTAL SLIDING INDICATOR
        // --------------------------------------
        slideTransition = new TranslateTransition(Duration.millis(300), navIndicator);
        fadeTransition = new FadeTransition(Duration.millis(200), navIndicator);
        navIndicator.setOpacity(0.0);

        setupSlidingHover(homeButton);
        setupSlidingHover(myCourseButton);
        setupSlidingHover(browseButton);

        themeToggleBtn.setOnMouseEntered(e -> {
            fadeTransition.stop();
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        });

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
        browseButton.setOnAction(e -> showBrowse());

        Platform.runLater(() -> {
            rootPane.getScene().getStylesheets().add(getClass().getResource("/CSS/scene1.css").toExternalForm());
        });
        // --------------------------------------
        // PART 2 — SIDEBAR VERTICAL SLIDING INDICATOR
        // --------------------------------------
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
        // ---------------------------------------
        // PART 3 — WELCOME & STATS
        // ---------------------------------------
        welcomeLabel.setText("Welcome");
        welcomeSub.setText("52 courses you have enrolled in");

        enrolledNum.setText("52");
        completedNum.setText(String.valueOf(countDownloadedFiles()));

        // ---------------------------------------
        // PART 4 — CARD HOVER ANIMATIONS
        // ---------------------------------------
        Platform.runLater(() -> {
            setupCardHoverAnimation(statCard1);
            setupCardHoverAnimation(statCard2);
        });

        // ---------------------------------------
        // PART 5 — ACCORDION (starts collapsed)
        // ---------------------------------------
        Platform.runLater(() -> {
            PauseTransition wait = new PauseTransition(Duration.millis(200));
            wait.setOnFinished(e -> {

                HBox yearOneRow = (HBox) yearOneSection.getChildren().get(2);
                HBox yearTwoRow = (HBox) yearTwoSection.getChildren().get(1);
                HBox yearThreeRow = (HBox) yearThreeSection.getChildren().get(1);
                HBox yearFourRow = (HBox) yearFourSection.getChildren().get(1);

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
            ScrollPane sp = mainScrollPane;

            // Get the scrollbar node
            sp.skinProperty().addListener((obs, oldSkin, newSkin) -> {
                if (newSkin != null) {
                    Node vbar = sp.lookup(".scroll-bar:vertical");
                    if (vbar != null) {
                        vbar.setOpacity(0);
                        // Show on scroll
                        sp.setOnScroll(e -> {
                            vbar.setOpacity(1.0);
                            // Fade out after 1.5 seconds of no scrolling
                            PauseTransition hide = new PauseTransition(Duration.millis(1500));
                            hide.setOnFinished(ev -> {
                                FadeTransition fade = new FadeTransition(Duration.millis(400), vbar);
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
        // Load each year on background thread — never block JavaFX thread
        loadYearAsync("FIRST", courseContainer1);
        loadYearAsync("SECOND", courseContainer2);
        loadYearAsync("THIRD", courseContainer3);
        loadYearAsync("FOURTH", courseContainer4);
    }

    private void loadYearAsync(String year, VBox container) {
        new Thread(() -> {
            try {
                System.out.println("Fetching year: " + year);
                List<courseModel> courses = courseService.fetchCoursesByYear(year);

                System.out.println("Got " + courses.size() + " courses for year: " + year);

                // UI updates must be on JavaFX thread
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

                // Show error card in UI
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
        nameLabel.getStyleClass().add("course-label");
        nameLabel.setWrapText(true);

        card.getChildren().addAll(codeLabel, nameLabel);

        // Base card style
        card.getStyleClass().add("card");

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

    // -----------------------------------------------------------
    // NAVBAR METHODS
    // -----------------------------------------------------------
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

    // -----------------------------------------------------------
    // SIDEBAR TOGGLE
    // -----------------------------------------------------------
    @FXML
    public void toggleSidebar() {

        if (sidebarOpen) {
            // ── COLLAPSE 
            Timeline collapse = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(sidebarPane.prefWidthProperty(), SIDEBAR_WIDTH, Interpolator.EASE_IN),
                            new KeyValue(sidebarPane.opacityProperty(), 1.0, Interpolator.EASE_IN)
                    ),
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(sidebarPane.prefWidthProperty(), 0, Interpolator.EASE_IN),
                            new KeyValue(sidebarPane.opacityProperty(), 0.0, Interpolator.EASE_IN)
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
            // ── EXPAND 
            sidebarPane.setVisible(true);
            sidebarPane.setManaged(true);
            sidebarPane.setPrefWidth(0);
            sidebarPane.setOpacity(0);

            // Show menu icon before sidebar expands
            swapIcon(true);

            Timeline expand = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(sidebarPane.prefWidthProperty(), 0, Interpolator.EASE_OUT),
                            new KeyValue(sidebarPane.opacityProperty(), 0.0, Interpolator.EASE_OUT)
                    ),
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(sidebarPane.prefWidthProperty(), SIDEBAR_WIDTH, Interpolator.EASE_OUT),
                            new KeyValue(sidebarPane.opacityProperty(), 1.0, Interpolator.EASE_OUT)
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

    // ----------------------------------------------------------
    // SIDEBAR INDICATOR METHODS
    // ----------------------------------------------------------
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

    // -----------------------------------------------------------
    // FLYOUT / CONTEXT MENU METHODS
    // -----------------------------------------------------------
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

        // ── Year menus 
        Menu csyear1 = new Menu("Year I");
        Menu csyear2 = new Menu("Year II");
        Menu csyear3 = new Menu("Year III");
        Menu csyear4 = new Menu("Year IV");

        departmentsFlyout.getItems().addAll(csyear1, csyear2, csyear3, csyear4);
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
            addCoursesToMenu(year1, courseService.fetchCoursesByYear("FIRST"));

            addCoursesToMenu(year2, courseService.fetchCoursesByYear("SECOND"));

            addCoursesToMenu(year3, courseService.fetchCoursesByYear("THIRD"));

            addCoursesToMenu(year4, courseService.fetchCoursesByYear("FOURTH"));

            departmentsFlyout.getItems().setAll(year1, year2, year3, year4);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void addCoursesToMenu(
            Menu menu,
            List<courseModel> courses) {

        for (courseModel course : courses) {

            MenuItem item = new MenuItem(course.getCourseName());

            item.setOnAction(e -> openCourse(course));
            menu.getItems().add(item);
        }
    }

    private void openCourse(courseModel course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML1/CoursePage.fxml"));
            Parent root = loader.load();

            CoursePageController controller = loader.getController();
            controller.setCourse(course);
            controller.applyCurrentTheme();

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

    public courseResourceModel getNotesPdf(int courseId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/resources/"
                + courseId + "/notes")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), courseResourceModel.class
        );
    }

    private void fadeIndicatorWhenMouseEntersFlyout(ContextMenu flyout) {
        flyout.setOnShown(e -> {
            Node content = flyout.getSkin().getNode();
            if (content != null) {

                // Slide down animation on flyout open
                content.setScaleY(0.0);
                content.setTranslateY(-content.getBoundsInLocal().getHeight() / 2);
                content.setOpacity(0.0);

                Timeline slideDown = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(content.scaleYProperty(), 0.0, Interpolator.EASE_OUT),
                                new KeyValue(content.translateYProperty(), -content.getBoundsInLocal().getHeight() / 2, Interpolator.EASE_OUT),
                                new KeyValue(content.opacityProperty(), 0.0, Interpolator.EASE_OUT)
                        ),
                        new javafx.animation.KeyFrame(Duration.millis(200),
                                new javafx.animation.KeyValue(
                                        content.scaleYProperty(), 1.0,
                                        Interpolator.EASE_OUT),
                                new KeyValue(
                                        content.translateYProperty(), 0.0,
                                        Interpolator.EASE_OUT),
                                new KeyValue(
                                        content.opacityProperty(), 1.0, Interpolator.EASE_OUT)
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
                    Timeline slideUp = new javafx.animation.Timeline(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(content.scaleYProperty(), 1.0, Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(
                                            content.translateYProperty(), 0.0,
                                            javafx.animation.Interpolator.EASE_IN),
                                    new javafx.animation.KeyValue(
                                            content.opacityProperty(), 1.0,
                                            javafx.animation.Interpolator.EASE_IN)
                            ),
                            new javafx.animation.KeyFrame(Duration.millis(150),
                                    new javafx.animation.KeyValue(content.scaleYProperty(), 0.0, Interpolator.EASE_IN),
                                    new KeyValue(content.translateYProperty(), -content.getBoundsInLocal().getHeight() / 2, Interpolator.EASE_IN),
                                    new KeyValue(content.opacityProperty(), 0.0, Interpolator.EASE_IN)
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

    // -----------------------------------------------------------
    // CARD HOVER ANIMATION
    // -----------------------------------------------------------
    private void setupCardHoverAnimation(VBox card) {

        DoubleProperty angle = new SimpleDoubleProperty(0);

        Timeline rotateGradient = new Timeline(
                new KeyFrame(Duration.millis(16), e -> {
                    double a = angle.get();
                    double rad = Math.toRadians(a);

                    double x1 = 50 + Math.cos(rad) * 50;
                    double y1 = 50 + Math.sin(rad) * 50;
                    double x2 = 50 - Math.cos(rad) * 50;
                    double y2 = 50 - Math.sin(rad) * 50;

                    String borderColor1;
                    String borderColor2;

                    if (themeManager.isDarkMode()) {
                        borderColor1 = "rgba(255,255,255,0.6)";
                        borderColor2 = "rgba(255,255,255,1.0)";
                    } else {
                        borderColor1 = "rgba(0,0,0,0.6)";
                        borderColor2 = "rgba(0,0,0,1.0)";
                    }
                    String cardBackground = themeManager.isDarkMode() ? "#1e293b" : "#D5DAE3";
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

        ScaleTransition liftIn = new ScaleTransition(Duration.millis(200), card);
        liftIn.setToX(1.02);
        liftIn.setToY(1.02);

        ScaleTransition liftOut = new ScaleTransition(Duration.millis(200), card);
        liftOut.setToX(1.0);
        liftOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            rotateGradient.play();
            liftIn.play();
        });

        String cardBackground = themeManager.isDarkMode() ? "#030202" : "#F8FAFC";
        String cardBorder = themeManager.isDarkMode() ? "#334155" : "#CBD5E1";
        card.setOnMouseExited(e -> {
            rotateGradient.stop();
            liftOut.play();
            card.setStyle(
                    cardBackground + ";"
                    + "-fx-background-radius: 12;"
                    + cardBorder + ";"
                    + "-fx-border-radius: 12;"
                    + "-fx-border-width: 1;"
                    + "-fx-padding: 16;"
                    + "-fx-spacing: 6;"
                    + "-fx-cursor: hand;"
            );
        });
    }

    // ----------------------------------------------------------
    // ACCORDION METHODS
    // ----------------------------------------------------------
    private void setupAccordion(Button headerBtn, ImageView arrowImg, HBox... contentRows) {
        boolean[] isOpen = {true};
        List<HBox> rows = Arrays.asList(contentRows);

        headerBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 0;"
        );

        arrowImg.setRotate(90);

        headerBtn.setOnMouseClicked(e -> {

            if (isOpen[0]) {
                // ── COLLAPSE 
                for (HBox row : rows) {
                    double startH = row.getHeight();

                    Timeline collapse = new Timeline(new KeyFrame(Duration.ZERO,
                            new KeyValue(row.maxHeightProperty(), startH, Interpolator.EASE_IN),
                            new KeyValue(row.opacityProperty(), 1.0, Interpolator.EASE_IN)
                    ),
                            new KeyFrame(Duration.millis(300),
                                    new KeyValue(row.maxHeightProperty(), 0, Interpolator.EASE_IN),
                                    new KeyValue(row.opacityProperty(), 0.0, Interpolator.EASE_IN)
                            )
                    );
                    collapse.setOnFinished(ev -> {
                        row.setVisible(false);
                        row.setManaged(false);
                    });
                    collapse.play();
                }

                RotateTransition rotateClose = new RotateTransition(Duration.millis(300), arrowImg);
                rotateClose.setFromAngle(90);
                rotateClose.setToAngle(0);
                rotateClose.play();

            } else {
                // ── EXPAND 
                for (HBox row : rows) {
                    row.setVisible(true);
                    row.setManaged(true);
                    row.setMaxHeight(Double.MAX_VALUE);
                    row.setOpacity(1.0);

                    row.applyCss();
                    row.layout();
                    double targetH = row.prefHeight(-1);

                    Timeline expand = new Timeline(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(row.maxHeightProperty(), 0, Interpolator.EASE_OUT),
                                    new KeyValue(row.opacityProperty(), 0.0, Interpolator.EASE_OUT)
                            ),
                            new KeyFrame(Duration.millis(300),
                                    new KeyValue(row.maxHeightProperty(), targetH, Interpolator.EASE_OUT),
                                    new KeyValue(row.opacityProperty(), 1.0, Interpolator.EASE_OUT)
                            )
                    );
                    expand.setOnFinished(ev
                            -> row.setMaxHeight(Double.MAX_VALUE));
                    expand.play();
                }

                RotateTransition rotateOpen = new RotateTransition(Duration.millis(300), arrowImg);
                rotateOpen.setFromAngle(0);
                rotateOpen.setToAngle(90);
                rotateOpen.play();
            }

            isOpen[0] = !isOpen[0];
        });
    }

    private void showHome() {
        if (homeContent != null) {
            rootPane.setLeft(sidebarPane);
            rootPane.setCenter(homeContent);
        }
        setNavActive(homeButton);
    }

    private void showMyCourses() {
        if (homeContent == null) {
            homeContent = rootPane.getCenter();
        }

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
            btn.getStyleClass().remove("nav-active");
        }
        active.getStyleClass().add("nav-active");
    }

    private VBox buildMyCoursesView() {
        VBox root = new VBox(20);
        root.getStyleClass().add("my-course");

        // ── Header 
        Label title = new Label("My Resources");
        title.getStyleClass().add("my-course-label");

        Label sub = new Label("Files you have downloaded");
        sub.getStyleClass().add("my-course-sub-label");

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
        nameLabel.getStyleClass().add("my-course-name-label");

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
        openBtn.getStyleClass().add("my-course-btn");
        openBtn.setOnMouseEntered(e -> openBtn.getStyleClass().add("my-course-btn-entered"));
        openBtn.setOnMouseExited(e -> openBtn.getStyleClass().add("my-course-btn-exited"));
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
        card.getStyleClass().add("my-course-card");

        card.setOnMouseEntered(e -> card.getStyleClass().add("my-course-card-entered"));
        card.setOnMouseExited(e -> card.getStyleClass().add("my-course-card-exited"));

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
            controller.applyCurrentTheme();

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

    private TextField browseSearchField;
    private VBox browseResultsContainer;

    private void showBrowse() {
        // Save home content first time
        if (homeContent == null) {
            homeContent = rootPane.getCenter();
        }

        setNavActive(browseButton);

        // Build browse view
        VBox browseContent = buildBrowseView();

        ScrollPane sp = new ScrollPane(browseContent);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-background: transparent;");

        rootPane.setCenter(sp);
    }

    private VBox buildBrowseView() {
        VBox root = new VBox(20);
        root.getStyleClass().add("browse");

        // ── Page title 
        Label title = new Label("Browse Resources");
        title.getStyleClass().add("browse-label");

        Label sub = new Label("Search for courses and download their PDFs");
        sub.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");

        VBox header = new VBox(4, title, sub);

        // ── Search bar 
        browseSearchField = new TextField();
        browseSearchField.setPromptText("🔍  Search courses e.g. \"data\", \"network\"...");
        browseSearchField.getStyleClass().add("browse-search-field");
        browseSearchField.setPrefHeight(46);

        // Focus style
        browseSearchField.focusedProperty().addListener((obs, old, focused) -> {
            if (focused) {
                browseSearchField.getStyleClass().add("browse-search-field-focused");
            } else {
                browseSearchField.getStyleClass().add("browse-search-field-focused-else");
            }
        });

        // Search on Enter key
        browseSearchField.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                performBrowseSearch(browseSearchField.getText().trim());
            }
        });

        // Search button
        Button searchBtn = new Button("Search");
        searchBtn.setStyle(
                "-fx-background-color: #6366f1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 12 24 12 24;"
                + "-fx-cursor: hand;");
        searchBtn.setOnMouseEntered(e -> searchBtn.setStyle(
                "-fx-background-color: #4f46e5;"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 12 24 12 24;"
                + "-fx-cursor: hand;"));
        searchBtn.setOnMouseExited(e -> searchBtn.setStyle(
                "-fx-background-color: #6366f1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 12 24 12 24;"
                + "-fx-cursor: hand;"));
        searchBtn.setOnAction(e -> performBrowseSearch(browseSearchField.getText().trim()));

        HBox searchRow = new HBox(10, browseSearchField, searchBtn);
        searchRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        HBox.setHgrow(browseSearchField, javafx.scene.layout.Priority.ALWAYS);

        // ── Results container 
        browseResultsContainer = new VBox(12);
        browseResultsContainer.setStyle("-fx-padding: 4 0 0 0;");

        // Initial hint
        Label hint = new Label("Type a course name above and press Search or Enter");
        hint.setStyle("-fx-text-fill: #334155; -fx-font-size: 13px;");
        browseResultsContainer.getChildren().add(hint);

        root.getChildren().addAll(
                header, searchRow, browseResultsContainer);

        return root;
    }

    private void performBrowseSearch(String query) {
        if (query == null || query.isEmpty()) {
            return;
        }

        browseResultsContainer.getChildren().clear();

        // Loading indicator
        Label loading = new Label("⏳ Searching for \"" + query + "\"...");
        loading.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        browseResultsContainer.getChildren().add(loading);

        new Thread(() -> {
            try {
                String url = "http://localhost:8080/api/courses/search?name=" + encode(query, StandardCharsets.UTF_8);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(url)).GET().build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Browse search response: " + response.body());

                ObjectMapper mapper = new ObjectMapper();
                courseModel[] courses = mapper.readValue(response.body(), courseModel[].class);

                Platform.runLater(() -> {
                    browseResultsContainer.getChildren().clear();

                    if (courses == null || courses.length == 0) {
                        Label none = new Label(
                                "No courses found for \"" + query + "\"");
                        none.setStyle(
                                "-fx-text-fill: #475569; -fx-font-size: 13px;");
                        browseResultsContainer.getChildren().add(none);
                        return;
                    }

                    // Results header
                    Label resultCount = new Label(
                            courses.length + " course"
                            + (courses.length != 1 ? "s" : "")
                            + " found for \"" + query + "\"");
                    resultCount.setStyle(
                            "-fx-text-fill: #6366f1;"
                            + "-fx-font-size: 12px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-padding: 0 0 4 0;");
                    browseResultsContainer.getChildren().add(resultCount);

                    // Build a card for each course
                    for (courseModel course : courses) {
                        VBox card = buildBrowseCourseCard(course);
                        browseResultsContainer.getChildren().add(card);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    browseResultsContainer.getChildren().clear();
                    Label err = new Label("Search failed: " + ex.getMessage());
                    err.setStyle(
                            "-fx-text-fill: #ef4444; -fx-font-size: 13px;");
                    browseResultsContainer.getChildren().add(err);
                });
            }
        }).start();
    }

    private VBox buildBrowseCourseCard(courseModel course) {
        VBox card = new VBox(10);
        card.getStyleClass().add("browse-card");

        // ── Course header 
        Label codeLabel = new Label(course.getCode());
        codeLabel.setStyle(
                "-fx-background-color: #312e81;"
                + "-fx-text-fill: #a5b4fc;"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 6;"
                + "-fx-padding: 3 10 3 10;");

        Label nameLabel = new Label(course.getCourseName());
        nameLabel.getStyleClass().add("browse-card-label");
        nameLabel.setWrapText(true);

        HBox courseHeader = new HBox(10, codeLabel, nameLabel);
        courseHeader.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // ── Resources section 
        Label resourcesTitle = new Label("📄 Resources");
        resourcesTitle.setStyle(
                "-fx-text-fill: #64748b;"
                + "-fx-font-size: 12px;"
                + "-fx-padding: 4 0 0 0;");

        VBox resourcesList = new VBox(8);
        resourcesList.setStyle("-fx-padding: 4 0 0 0;");

        // Loading indicator for resources
        Label loadingRes = new Label("Loading resources...");
        loadingRes.setStyle("-fx-text-fill: #475569; -fx-font-size: 12px;");
        resourcesList.getChildren().add(loadingRes);

        // Expand/collapse toggle
        Button toggleBtn = new Button("▼ View Resources");
        toggleBtn.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: #334155;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-text-fill: #6366f1;"
                + "-fx-font-size: 12px;"
                + "-fx-cursor: hand;"
                + "-fx-padding: 6 12 6 12;");

        // Resources start hidden
        resourcesList.setVisible(false);
        resourcesList.setManaged(false);

        final boolean[] expanded = {false};

        toggleBtn.setOnAction(e -> {
            if (!expanded[0]) {
                // Expand and load resources
                resourcesList.setVisible(true);
                resourcesList.setManaged(true);
                toggleBtn.setText("▲ Hide Resources");
                expanded[0] = true;

                // Load resources from API
                loadBrowseResources(course, resourcesList);
            } else {
                // Collapse
                resourcesList.setVisible(false);
                resourcesList.setManaged(false);
                toggleBtn.setText("▼ View Resources");
                expanded[0] = false;
            }
        });

        card.getChildren().addAll(
                courseHeader, toggleBtn, resourcesList);

        // Card hover
        card.setOnMouseEntered(ev -> card.getStyleClass().add("browse-card-entered"));
        card.setOnMouseExited(ev -> card.getStyleClass().add("browse-card-exited"));

        return card;
    }

    private void loadBrowseResources(courseModel course, VBox resourcesList) {
        new Thread(() -> {
            try {
                courseResourceService serviceInstance = new courseResourceService();

                List<courseResourceModel> fetched = serviceInstance.fetchCourseResources(course.getId());

                Platform.runLater(() -> {
                    resourcesList.getChildren().clear();

                    if (fetched == null || fetched.isEmpty()) {
                        Label none = new Label("No resources available.");
                        none.setStyle(
                                "-fx-text-fill: #475569; -fx-font-size: 12px;");
                        resourcesList.getChildren().add(none);
                        return;
                    }

                    for (courseResourceModel resource : fetched) {
                        HBox resRow = buildBrowseResourceRow(
                                course, resource);
                        resourcesList.getChildren().add(resRow);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    resourcesList.getChildren().clear();
                    Label err = new Label(
                            "Failed to load resources.");
                    err.setStyle(
                            "-fx-text-fill: #ef4444; -fx-font-size: 12px;");
                    resourcesList.getChildren().add(err);
                });
            }
        }).start();
    }

    private HBox buildBrowseResourceRow(courseModel course, courseResourceModel resource) {
        HBox row = new HBox(10);
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        row.getStyleClass().add("browse-row");

        // Choose icon based on type or file extension
        String typeStr = resource.getType() != null
                ? resource.getType().toUpperCase() : "";
        String fileStr = resource.getFileName() != null
                ? resource.getFileName().toLowerCase() : "";

        String icon
                = typeStr.equals("MIDTERM") ? "📝"
                : typeStr.equals("FINAL") ? "📑"
                : typeStr.equals("LINK") ? "🔗"
                : fileStr.endsWith(".pdf") ? "📄"
                : fileStr.endsWith(".jpg") || fileStr.endsWith(".png") ? "🖼"
                : "📄";

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 16px;");

        // Resource title
        Label titleLabel = new Label(resource.getTitle());
        titleLabel.getStyleClass().add("browse-row-title");
        titleLabel.setWrapText(true);
        HBox.setHgrow(titleLabel, javafx.scene.layout.Priority.ALWAYS);

        // Use fileName from model for existing file check
        File courseVaultFolder = new File(
                System.getProperty("user.home")
                + File.separator + "Downloads"
                + File.separator + "CourseVault");

        String safeName = getSafeFileName(resource);
        File existingFile = new File(courseVaultFolder, safeName);

        Button actionBtn;
        if (existingFile.exists()) {
            actionBtn = new Button("📂  Open");
            actionBtn.setStyle(
                    "-fx-background-color: #14532d;"
                    + "-fx-border-color: #22c55e;"
                    + "-fx-border-radius: 8;"
                    + "-fx-background-radius: 8;"
                    + "-fx-text-fill: #86efac;"
                    + "-fx-font-size: 11px;"
                    + "-fx-padding: 5 12 5 12;"
                    + "-fx-cursor: hand;");
            actionBtn.setOnAction(e -> openBrowseFile(existingFile));

        } else {
            actionBtn = new Button("⬇ Download");
            actionBtn.getStyleClass().add("browse-row-download-btn");

            final Button finalBtn = actionBtn;
            final String finalFileName = safeName;
            actionBtn.setOnAction(e
                    -> downloadBrowseResource(resource, finalBtn,
                            courseVaultFolder, finalFileName));
        }

        row.getChildren().addAll(iconLabel, titleLabel, actionBtn);

        row.setOnMouseEntered(e -> row.getStyleClass().add("browse-row-entered"));
        row.setOnMouseExited(e -> row.getStyleClass().add("browse-row-exited"));

        return row;
    }

    private void downloadBrowseResource(courseResourceModel resource, Button btn, File folder, String fileName) {
        btn.setDisable(true);
        btn.setText("⬇ Downloading...");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Use fileName from model if available, otherwise use title
        String actualFileName = (resource.getFileName() != null
                && !resource.getFileName().isEmpty())
                ? resource.getFileName()
                : fileName;

        File destination = new File(folder, actualFileName);

        new Thread(() -> {
            try {
                // Use downloadUrl directly from the model
                String downloadUrl = resource.getDownloadUrl();

                if (downloadUrl == null || downloadUrl.isEmpty()) {
                    throw new Exception(
                            "No download URL available for: "
                            + resource.getTitle());
                }

                // Add base URL if relative path
                if (!downloadUrl.startsWith("http")) {
                    downloadUrl = "http://localhost:8080" + downloadUrl;
                }

                System.out.println("Downloading from: " + downloadUrl);
                System.out.println("Saving to: " + destination.getAbsolutePath());

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(downloadUrl)).GET().build();

                HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                System.out.println("Status: " + response.statusCode());

                if (response.statusCode() == 200) {
                    java.nio.file.Files.copy(
                            response.body(),
                            destination.toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("✅ Saved: "
                            + destination.length() + " bytes");

                    Platform.runLater(() -> {
                        btn.setDisable(false);
                        btn.setText("📂  Open");
                        btn.setStyle(
                                "-fx-background-color: #14532d;"
                                + "-fx-border-color: #22c55e;"
                                + "-fx-border-radius: 8;"
                                + "-fx-background-radius: 8;"
                                + "-fx-text-fill: #86efac;"
                                + "-fx-font-size: 11px;"
                                + "-fx-padding: 5 12 5 12;"
                                + "-fx-cursor: hand;");
                        btn.setOnAction(e -> openBrowseFile(destination));
                        openBrowseFile(destination);
                    });

                } else {
                    byte[] errBytes = response.body().readAllBytes();
                    String errBody = new String(errBytes,
                            java.nio.charset.StandardCharsets.UTF_8);
                    System.err.println("❌ Failed: "
                            + response.statusCode() + " — " + errBody);
                    throw new Exception("Server returned "
                            + response.statusCode());
                }

            } catch (Exception ex) {
                System.err.println("Download error: " + ex.getMessage());
                ex.printStackTrace();
                Platform.runLater(() -> {
                    btn.setDisable(false);
                    btn.setText("⬇  Retry");
                    btn.setStyle(
                            "-fx-background-color: #7f1d1d;"
                            + "-fx-border-color: #ef4444;"
                            + "-fx-border-radius: 8;"
                            + "-fx-background-radius: 8;"
                            + "-fx-text-fill: #fca5a5;"
                            + "-fx-font-size: 11px;"
                            + "-fx-padding: 5 12 5 12;"
                            + "-fx-cursor: hand;");
                });
            }
        }).start();
    }

    private void openBrowseFile(File file) {
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

    @FXML
    private void toggleTheme(ActionEvent event) {
        themeManager.toggleTheme();
        Scene scene = rootPane.getScene();
        if (scene == null) {
            return;
        }
        scene.getStylesheets().clear();
        if (!themeManager.isDarkMode()) {
            scene.getStylesheets().add(getClass().getResource("/CSS/lightmode.css").toExternalForm());
            themeToggleBtn.setText("🌙");
            System.out.println("LIGHT MODE");
        } else {
            scene.getStylesheets().add(getClass().getResource("/CSS/scene1.css").toExternalForm());
            themeToggleBtn.setText("☀");
            System.out.println("DARK MODE");
        }
        scene.getRoot().applyCss();
        scene.getRoot().layout();
    }

}
