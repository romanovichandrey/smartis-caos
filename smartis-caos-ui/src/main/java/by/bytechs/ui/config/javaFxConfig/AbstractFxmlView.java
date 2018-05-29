package by.bytechs.ui.config.javaFxConfig;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * <p>
 * Base class for fxml-based view classes.
 * {@link AbstractFxmlView} that provides DI for Java FX Controllers via Spring.
 * Supports annotation driven creation of FXML based view beans with {@link FXMLView}
 *
 * @author Romanovich Andrei
 */
public abstract class AbstractFxmlView implements ApplicationContextAware {
    private static final Logger LOGGER = LogManager.getLogger(AbstractFxmlView.class);
    private final ObjectProperty<Object> presenterProperty;
    private final Optional<ResourceBundle> bundle;
    private final URL resource;
    private final FXMLView annotation;
    private FXMLLoader fxmlLoader;
    private ApplicationContext applicationContext;
    private String fxmlRoot;

    /**
     * <p>
     * Instantiates a new abstract fxml view.
     */
    public AbstractFxmlView() {
        //TODO: Set the root path to package path
        final String filePathFromPackageName = PropertyReaderHelper.determineFilePathFromPackageName(getClass());
        setFxmlRootPath(filePathFromPackageName);
        annotation = getFXMLAnnotation();
        resource = getURLResource(annotation);
        presenterProperty = new SimpleObjectProperty<>();
        bundle = getResourceBundle(getBundleName());
    }

    /**
     * <p>
     * Gets the URL resource. This will be derived from applied annotation value
     * or from naming convention.
     *
     * @param annotation the annotation as defined by inheriting class.
     * @return the URL resource
     */
    private URL getURLResource(final FXMLView annotation) {
        if (annotation != null && !annotation.value().equals("")) {
            return getClass().getResource(annotation.value());
        } else {
            return getClass().getResource(getFxmlPath());
        }
    }

    /**
     * <p>
     * Gets the {@link FXMLView} annotation from inheriting class.
     *
     * @return the FXML annotation
     */
    private FXMLView getFXMLAnnotation() {
        final Class<? extends AbstractFxmlView> theClass = this.getClass();
        final FXMLView annotation = theClass.getAnnotation(FXMLView.class);
        return annotation;
    }

    /**
     * <p>
     * Creates the controller for type.
     *
     * @param type the type
     * @return the object
     */
    private Object createControllerForType(final Class<?> type) {
        return applicationContext.getBean(type);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext != null) {
            return;
        }
        this.applicationContext = applicationContext;
    }

    private void setFxmlRootPath(final String path) {
        fxmlRoot = path;
    }

    /**
     * <p>
     * Load synchronously.
     *
     * @param resource the resource
     * @param bundle   the bundle
     * @return the FXML loader
     * @throws IllegalStateException the illegal state exception
     */
    private FXMLLoader loadSynchronously(final URL resource, final Optional<ResourceBundle> bundle) throws IllegalStateException {
        final FXMLLoader loader = new FXMLLoader(resource, bundle.orElse(null));
        loader.setControllerFactory(this::createControllerForType);
        try {
            loader.load();
        } catch (final IOException | IllegalStateException e) {
            throw new IllegalStateException("Cannot load " + getConventionalName(), e);
        }
        return loader;
    }

    /**
     * <p>
     * Ensure fxml loader initialized.
     */
    private void ensureFxmlLoaderInitialized() {
        if (fxmlLoader != null) {
            return;
        }
        fxmlLoader = loadSynchronously(resource, bundle);
        presenterProperty.set(fxmlLoader.getController());
    }

    /**
     * <p>
     * Initializes the view by loading the FXML (if not happened yet) and
     * returns the top Node (parent) specified in the FXML file.
     *
     * @return the root view as determined from {@link FXMLLoader}.
     */
    public Parent getView() {
        ensureFxmlLoaderInitialized();
        final Parent parent = fxmlLoader.getRoot();
        addCSSIfAvailable(parent);
        return parent;
    }

    /**
     * <p>
     * Initializes the view synchronously and invokes the consumer with the
     * created parent Node within the FX UI thread.
     *
     * @param consumer - an object interested in received the {@link Parent} as callback
     */
    public void getView(final Consumer<Parent> consumer) {
        CompletableFuture.supplyAsync(this::getView, Platform::runLater).thenAccept(consumer);
    }

    /**
     * <p>
     * Scene Builder creates for each FXML document a root container. This
     * method omits the root container (e.g. {@link javafx.scene.layout.AnchorPane}) and gives you
     * the access to its first child.
     *
     * @return the first child of the {@link javafx.scene.layout.AnchorPane} or null if there are no
     * children available from this view.
     */
    public Node getViewWithoutRootContainer() {
        final ObservableList<Node> children = getView().getChildrenUnmodifiable();
        if (children.isEmpty()) {
            return null;
        }
        return children.listIterator().next();
    }

    /**
     * <p>
     * Adds the CSS if available.
     *
     * @param parent the parent
     */
    void addCSSIfAvailable(final Parent parent) {
        // Read global css when available:
        final List<String> list = PropertyReaderHelper.get(applicationContext.getEnvironment(), "javafx.css");
        if (!list.isEmpty()) {
            list.forEach(css -> parent.getStylesheets().add(getClass().getResource(css).toExternalForm()));
        }
        addCSSFromAnnotation(parent);
        final URL uri = getClass().getResource(getStyleSheetName());
        if (uri == null) {
            return;
        }
        final String uriToCss = uri.toExternalForm();
        parent.getStylesheets().add(uriToCss);
    }

    /**
     * <p>
     * Adds the CSS from annotation to parent.
     *
     * @param parent the parent
     */
    private void addCSSFromAnnotation(final Parent parent) {
        if (annotation != null && annotation.css().length > 0) {
            for (final String cssFile : annotation.css()) {
                final URL uri = getClass().getResource(cssFile);
                if (uri != null) {
                    final String uriToCss = uri.toExternalForm();
                    parent.getStylesheets().add(uriToCss);
                    LOGGER.debug("css file added to parent: {}", cssFile);
                } else {
                    LOGGER.warn("referenced {} css file could not be located", cssFile);
                }
            }
        }
    }

    /**
     * <p>
     * Gets the default title for to be shown in a (un)modal window.
     */
    String getDefaultTitle() {
        return annotation.title();
    }

    /**
     * <p>
     * Gets the default style for a (un)modal window.
     */
    StageStyle getDefaultStyle() {
        final String style = annotation.stageStyle();
        return StageStyle.valueOf(style.toUpperCase());
    }

    /**
     * <p>
     * Gets the default icon for to be shown in a (un)modal window.
     */
    Collection<Image> getDefaultStageIcons() {
        List<Image> iconList = new ArrayList<>();
        if (annotation != null && annotation.stageIcons().length > 0) {
            for (final String stageIcon : annotation.stageIcons()) {
                Image icon = new Image(getClass().getResource(stageIcon).toExternalForm());
                iconList.add(icon);
            }
        }
        return iconList;
    }

    private void clearAll(ObservableList<Node> nodeList) {
        try {
            for (Node node : nodeList) {
                Method[] nodeMethods = node.getClass().getMethods();
                for (Method method : nodeMethods) {
                    if (method.getName().equals("getChildrenUnmodifiable")) {
                        clearAll((ObservableList<Node>) method.invoke(node));
                    }
                    if (method.getName().equals("clear")) {
                        method.invoke(node);
                    }
                    if (method.getName().equals("getSelectionModel")) {
                        if (node instanceof ComboBox) {
                            ((SingleSelectionModel) method.invoke(node)).clearSelection();
                        } else if (node instanceof TableView) {
                            ((TableSelectionModel) method.invoke(node)).clearSelection();
                        }
                    }
                    if (method.getName().equals("setSelected")) {
                        method.invoke(node, false);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void closeView() {
        Parent parent = getView();
        parent.requestFocus();
        parent.getScene().getWindow().hide();
        clearAll(parent.getChildrenUnmodifiable());
    }

    /**
     * <p>
     * Gets the style sheet name.
     *
     * @return the style sheet name
     */
    private String getStyleSheetName() {
        return fxmlRoot + getConventionalName(".css");
    }

    /**
     * <p>
     * In case the view was not initialized yet, the conventional fxml
     * (airhacks.fxml for the AirhacksView and AirhacksPresenter) are loaded and
     * the specified presenter / controller is going to be constructed and
     * returned.
     *
     * @return the corresponding controller / presenter (usually for a
     * AirhacksView the AirhacksPresenter)
     */
    public Object getPresenter() {
        ensureFxmlLoaderInitialized();
        return presenterProperty.get();
    }

    /**
     * <p>
     * Does not initialize the view. Only registers the Consumer and waits until
     * the the view is going to be created / the method FXMLView#getView or
     * FXMLView#getViewAsync invoked.
     *
     * @param presenterConsumer listener for the presenter construction
     */
    public void getPresenter(final Consumer<Object> presenterConsumer) {
        presenterProperty.addListener(
                (final ObservableValue<? extends Object> o, final Object oldValue, final Object newValue) -> {
                    presenterConsumer.accept(newValue);
                });
    }

    /**
     * <p>
     * Gets the conventional name.
     *
     * @param ending the suffix to append
     * @return the conventional name with stripped ending
     */
    private String getConventionalName(final String ending) {
        return getConventionalName() + ending;
    }

    /**
     * <p>
     * Gets the conventional name.
     *
     * @return the name of the view without the "View" prefix in lowerCase. For
     * AirhacksView just airhacks is going to be returned.
     */
    private String getConventionalName() {
        return stripEnding(getClass().getSimpleName().toLowerCase());
    }

    /**
     * <p>
     * Gets the bundle name.
     *
     * @return the bundle name
     */
    private String getBundleName() {
        if (StringUtils.isEmpty(annotation.bundle())) {
            final String lbundle = getClass().getPackage().getName() + "." + getConventionalName();
            LOGGER.debug("Bundle: {} based on conventional name.", lbundle);
            return lbundle;
        }
        final String lbundle = annotation.bundle();
        LOGGER.debug("Annotated bundle: {}", lbundle);
        return lbundle;
    }

    /**
     * <p>
     * Strip ending.
     *
     * @param clazz the clazz
     * @return the string
     */
    private static String stripEnding(final String clazz) {
        if (!clazz.endsWith("view")) {
            return clazz;
        }
        return clazz.substring(0, clazz.lastIndexOf("view"));
    }

    /**
     * <p>
     * Gets the fxml file path.
     *
     * @return the relative path to the fxml file derived from the FXML view.
     * e.g. The name for the AirhacksView is going to be
     * <PATH>/airhacks.fxml.
     */

    final String getFxmlPath() {
        final String fxmlPath = fxmlRoot + getConventionalName(".fxml");
        LOGGER.debug("Determined fxmlPath: " + fxmlPath);
        return fxmlPath;
    }

    /**
     * <p>
     * Returns a resource bundle if available
     *
     * @param name the name of the resource bundle.
     * @return the resource bundle
     */
    private Optional<ResourceBundle> getResourceBundle(final String name) {
        ResourceBundle bundle;
        try {
            LOGGER.debug("Resource bundle: " + name);
            bundle = ResourceBundle.getBundle(name);
        } catch (final MissingResourceException ex) {
            LOGGER.debug("No resource bundle could be determined: " + ex.getMessage());
            bundle = null;
        }
        return Optional.ofNullable(bundle);
    }

    /**
     * <p>
     * Gets the resource bundle.
     *
     * @return an existing resource bundle, or null
     */
    public Optional<ResourceBundle> getResourceBundle() {
        return bundle;
    }

    @Override
    public String toString() {
        return "AbstractFxmlView [presenterProperty=" + presenterProperty + ", bundle=" + bundle + ", resource="
                + resource + ", fxmlRoot=" + fxmlRoot + "]";
    }
}