package by.bytechs.ui.service.interfaces;

/**
 * @author Romanovich Andrei
 */
public interface PasswordDigestService {

    String getPasswordHash(String password);

    boolean isConfirmPassword(String password, String confirmPassword);
}