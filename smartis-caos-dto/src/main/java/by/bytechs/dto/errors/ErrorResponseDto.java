package by.bytechs.dto.errors;

import java.io.Serializable;

/**
 * <p>
 * It contains a response with the code and errors text, it is returned by
 * the server when there are server errors.
 *
 * @author Romanovich Andrei
 */
public class ErrorResponseDto implements Serializable {
    private int errorCode;
    private String errorMessage;

    public ErrorResponseDto() {
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
