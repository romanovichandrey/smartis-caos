package by.bytechs.web.exceptions;

import by.bytechs.util.exceptions.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * It is designed to log web level exceptions
 *
 * @author Romanovich Andrei
 * @see BaseException
 */
public class WebException extends BaseException {
    private String message;
    private final Logger webExceptionLog = LogManager.getLogger("WebLogger");

    /**
     * Constructs a new webException
     *
     * @param e
     * @param params
     */
    public WebException(Exception e, Object... params) {
        super(e, params);
        message = "[Location: " + getDescription(e) + "] [Description: " + e.getClass().getName() + " [" + e.getMessage() + "].";
        webExceptionLog.error(getMessage());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}