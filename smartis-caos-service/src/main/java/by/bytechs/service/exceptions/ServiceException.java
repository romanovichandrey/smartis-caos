package by.bytechs.service.exceptions;

import by.bytechs.util.exceptions.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * It is designed to log service level exceptions
 *
 * @author Romanovich Andrei
 */
public class ServiceException extends BaseException {
    private Object[] params;
    private String message;
    private final Logger serviceExceptionLog = LogManager.getLogger("ServiceLogger");

    /**
     * Constructs a new serviceException
     *
     * @param e
     * @param params
     */
    public ServiceException(Exception e, Object... params) {
        super(e, params);
        message = "[Location: " + getDescription(e) + "] [Description: " + e.getClass().getName() + " [" + e.getMessage() + "].";
        serviceExceptionLog.error(getMessage());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
