package by.bytechs.ui.config.javaFxConfig;


import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Romanovich Andrei
 */
public abstract class AbstractJavaFxApplication extends Application {
    private static Logger logger = LogManager.getLogger(AbstractJavaFxApplication.class);
    private static String[] savedArgs = new String[0];
    static Class<? extends AbstractFxmlView> savedInitialView;
    static SplashScreen splashScreen;
    private static ConfigurableApplicationContext applicationContext;
    private static List<Image> icons = new ArrayList<>();
    private final List<Image> defaultIcons = new ArrayList<>();
    private final BooleanProperty appCtxLoaded = new SimpleBooleanProperty(false);

    public static Stage getStage() {
        return GUIState.getStage();
    }

    public static Scene getScene() {
        return GUIState.getScene();
    }

    public static HostServices getAppHostServices() {
        return GUIState.getHostServices();
    }

    public static SystemTray getSystemTray() {
        return GUIState.getSystemTray();
    }

    /**
     * @param window The FxmlView derived class that should be shown.
     * @param mode   See {@code javafx.stage.Modality}.
     */
    public static void showView(final Class<? extends AbstractFxmlView> window, final Modality mode, Stage ownerStage) {
        final AbstractFxmlView view = applicationContext.getBean(window);

        Stage newStage = new Stage();

        Scene newScene;
        if (view.getView().getScene() != null) {
            // This view was already shown so
            // we have a scene for it and use this one.
            newScene = view.getView().getScene();
        } else {
            newScene = new Scene(view.getView());
        }

        newStage.setScene(newScene);
        newStage.initStyle(view.getDefaultStyle());
        newStage.getIcons().addAll(view.getDefaultStageIcons());
        newStage.initModality(mode);
        if (ownerStage != null) {
            newStage.initOwner(ownerStage);
        }
        newStage.setTitle(view.getDefaultTitle());
        newStage.setOnHiding(event -> view.closeView());
        newStage.showAndWait();
    }

    private void loadIcons(ConfigurableApplicationContext ctx) {
        try {
            final List<String> fsImages = PropertyReaderHelper.get(ctx.getEnvironment(), "javafx.appicons");
            if (!fsImages.isEmpty()) {
                fsImages.forEach((s) ->
                        {
                            Image img = new Image(getClass().getResource(s).toExternalForm());
                            icons.add(img);
                        }
                );
            } else { // add factory images
                icons.addAll(defaultIcons);
            }
        } catch (Exception e) {
            logger.error("Failed to load icons: ", e);
        }
    }

    @Override
    public void init() throws Exception {
        // Load in JavaFx Thread and reused by Completable Future, but should no be a big deal.
        defaultIcons.addAll(loadDefaultIcons());
        CompletableFuture.supplyAsync(() -> SpringApplication.run(this.getClass(), savedArgs)).whenComplete((ctx, throwable) -> {
            if (throwable != null) {
                logger.error("Failed to load spring application context: ", throwable);
                Platform.runLater(() -> showErrorAlert(throwable));
            } else {
                Platform.runLater(() -> {
                    loadIcons(ctx);
                    launchApplicationView(ctx);
                });
            }
        });
    }

    @Override
    public void start(final Stage stage) throws Exception {
        GUIState.setStage(stage);
        GUIState.setHostServices(this.getHostServices());
        final Stage splashStage = new Stage(StageStyle.TRANSPARENT);
        if (AbstractJavaFxApplication.splashScreen.visible()) {
            final Scene splashScene = new Scene(splashScreen.getParent(), javafx.scene.paint.Color.TRANSPARENT);
            splashStage.setScene(splashScene);
            splashStage.getIcons().addAll(defaultIcons);
            splashStage.initStyle(StageStyle.TRANSPARENT);
            beforeShowingSplash(splashStage);
            splashStage.show();
        }
        final Runnable showMainAndCloseSplash = () -> {
            showInitialView();
            if (AbstractJavaFxApplication.splashScreen.visible()) {
                splashStage.hide();
                splashStage.setScene(null);
            }
        };
        synchronized (this) {
            if (appCtxLoaded.get()) {
                // Spring ContextLoader was faster
                Platform.runLater(showMainAndCloseSplash);
            } else {
                appCtxLoaded.addListener((ov, oVal, nVal) -> {
                    Platform.runLater(showMainAndCloseSplash);
                });
            }
        }
    }

    /**
     * Show initial view.
     */
    private void showInitialView() {
        final String stageStyle = applicationContext.getEnvironment().getProperty("javafx.stage.style");
        if (stageStyle != null) {
            GUIState.getStage().initStyle(StageStyle.valueOf(stageStyle.toUpperCase()));
        } else {
            GUIState.getStage().initStyle(StageStyle.DECORATED);
        }
        beforeInitialView(GUIState.getStage(), applicationContext);
        showView(savedInitialView);
    }

    /**
     * Launch application view.
     */
    private void launchApplicationView(final ConfigurableApplicationContext ctx) {
        AbstractJavaFxApplication.applicationContext = ctx;
        appCtxLoaded.set(true);
    }

    /**
     * Show view.
     *
     * @param newView the new view
     */
    public static void showView(final Class<? extends AbstractFxmlView> newView) {
        try {
            final AbstractFxmlView view = applicationContext.getBean(newView);
            if (GUIState.getScene() == null) {
                GUIState.setScene(new Scene(view.getView()));
            } else {
                GUIState.getScene().setRoot(view.getView());
            }
            GUIState.getStage().setScene(GUIState.getScene());
            applyEnvPropsToView();
            GUIState.getStage().getIcons().addAll(icons);
            GUIState.getStage().setTitle(view.getDefaultTitle());
            GUIState.getStage().show();

        } catch (Throwable t) {
            logger.error("Failed to load application: ", t);
            showErrorAlert(t);
        }
    }

    /**
     * Show error alert that close app.
     *
     * @param throwable cause of error
     */
    private static void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "К сожалению! Произошла неустранимая ошибка.\n" +
                "Обратитесь к поставщику программного обеспечения.\n\n" +
                "Приложение остановится сейчас.");
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }

    /**
     * Apply env props to view.
     */
    private static void applyEnvPropsToView() {
        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.width", Double.class,
                GUIState.getStage()::setWidth);
        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.height", Double.class,
                GUIState.getStage()::setHeight);
        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.resizable", Boolean.class,
                GUIState.getStage()::setResizable);
        PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), "javafx.stage.maximized", Boolean.class,
                GUIState.getStage()::setMaximized);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.close();
        }
    }

    /**
     * Sets the title. Allows to overwrite values applied during construction at
     * a later time.
     *
     * @param title the new title
     */
    protected static void setTitle(final String title) {
        GUIState.getStage().setTitle(title);
    }

    /**
     * Launch app.
     *
     * @param appClass the app class
     * @param view     the view
     * @param args     the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplication> appClass,
                                 final Class<? extends AbstractFxmlView> view, final String[] args) {
        launchApp(appClass, view, new SplashScreen(), args);
    }

    /**
     * Launch app.
     *
     * @param appClass     the app class
     * @param view         the view
     * @param splashScreen the splash screen
     * @param args         the args
     */
    public static void launchApp(final Class<? extends AbstractJavaFxApplication> appClass,
                                 final Class<? extends AbstractFxmlView> view, final SplashScreen splashScreen,
                                 final String[] args) {
        savedInitialView = view;
        savedArgs = args;
        if (splashScreen != null) {
            AbstractJavaFxApplication.splashScreen = splashScreen;
        } else {
            AbstractJavaFxApplication.splashScreen = new SplashScreen();
        }
        if (SystemTray.isSupported()) {
            GUIState.setSystemTray(SystemTray.getSystemTray());
        }
        Application.launch(appClass, args);
    }

    /**
     * Gets called after full initialization of Spring application context
     * and JavaFX platform right before the initial view is shown.
     * Override this method as a hook to add special code for your app. Especially meant to
     * add AWT code to add a system tray icon and behavior by calling
     * GUIState.getSystemTray() and modifying it accordingly.
     * <p>
     * By default noop.
     *
     * @param stage can be used to customize the stage before being displayed
     * @param ctx   represents spring ctx where you can loog for beans.
     */
    public void beforeInitialView(final Stage stage, final ConfigurableApplicationContext ctx) {
    }

    public void beforeShowingSplash(Stage splashStage) {

    }

    public Collection<Image> loadDefaultIcons() {
        return Arrays.asList(new Image(getClass().getResource("/icons/tray/default/gear_16x16.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/default/gear_24x24.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/default/gear_36x36.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/default/gear_42x42.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/default/gear_64x64.png").toExternalForm()));
    }
}