package by.bytechs.ui.service;

import by.bytechs.ui.service.interfaces.PasswordDigestService;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Romanovich Andrei
 */
@Service
public class PasswordDigestServiceImpl implements PasswordDigestService {
    private final Logger errorLogger = LogManager.getLogger(PasswordDigestServiceImpl.class);

    @Override
    public String getPasswordHash(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-512");
            md5.update(password.getBytes());
            byte[] digest = md5.digest(password.getBytes());
            String passwordHash = DatatypeConverter.printHexBinary(digest);
            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            errorLogger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}