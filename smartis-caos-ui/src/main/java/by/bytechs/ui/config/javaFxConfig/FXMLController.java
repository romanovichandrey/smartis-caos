package by.bytechs.ui.config.javaFxConfig;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * The annotation {@link FXMLController} is used to mark JavaFX controller
 * classes. Usage of this annotation happens besides registration of such within
 * fxml descriptors.
 *
 * @author Romanovich Andrei
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLController {
}