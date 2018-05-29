package by.bytechs.ui;

import by.bytechs.ui.config.javaFxConfig.AbstractJavaFxApplication;
import by.bytechs.ui.config.javaFxConfig.SplashScreen;
import by.bytechs.ui.view.RootLayoutView;
import javafx.scene.image.Image;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Kotsuba_VV on 17.02.2017.
 */
@SpringBootApplication
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class MainApp extends AbstractJavaFxApplication {
    public static void main(String[] args) {
        final SplashScreen splashScreen = new SplashScreen("/icons/splash/bytechs.png");
        launchApp(MainApp.class, RootLayoutView.class, splashScreen, args);
    }

    @Override
    public Collection<Image> loadDefaultIcons() {
        return Arrays.asList(new Image(getClass().getResource("/icons/tray/bytechs/bytechs16_16.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/bytechs/bytechs24_24.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/bytechs/bytechs36_36.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/bytechs/bytechs42_42.png").toExternalForm()),
                new Image(getClass().getResource("/icons/tray/bytechs/bytechs64_64.png").toExternalForm()));
    }
}
