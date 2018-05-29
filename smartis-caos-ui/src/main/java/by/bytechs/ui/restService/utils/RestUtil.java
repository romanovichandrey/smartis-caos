package by.bytechs.ui.restService.utils;


import by.bytechs.dto.errors.ErrorResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class overall for getting dto from rest service.
 *
 * @author Romanovich Andrei
 */
public class RestUtil<T> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger errorLogger = LogManager.getLogger(RestUtil.class);

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    /**
     * This method will deserialize with json boolean type or ErrorResponseDto
     *
     * @param responseEntity
     * @return boolean type
     */
    public static boolean isResultAction(ResponseEntity<String> responseEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = responseEntity.getBody();
        try {
            if (isError(responseEntity.getStatusCode())) {
                ErrorResponseDto errorDto = objectMapper.readValue(responseBody, ErrorResponseDto.class);
                if (errorDto != null) {
                    errorLogger.error(errorDto.getErrorMessage());
                } else {
                    errorLogger.error("Not know exception");
                }
            } else {
                return objectMapper.readValue(responseBody, Boolean.class);
            }
            return false;
        } catch (IOException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
            return false;
        }
    }

    /**
     * This method will deserialize with json T collection type or ErrorResponseDto
     *
     * @param responseEntity
     * @param typeReference
     * @return T
     */
    public T getResultActionCollection(ResponseEntity<String> responseEntity, TypeReference<T> typeReference,
                                       SimpleModule... simpleModules) {

        mapper.registerModules(simpleModules);

        String responseBody = responseEntity.getBody();
        try {
            if (isError(responseEntity.getStatusCode())) {
                ErrorResponseDto errorDto = mapper.readValue(responseBody, ErrorResponseDto.class);
                if (errorDto != null) {
                    errorLogger.error(errorDto.getErrorMessage());
                } else {
                    errorLogger.error("Not know exception");
                }
            } else {
                T t = mapper.readValue(responseBody, typeReference);
                return t;
            }
            return null;
        } catch (IOException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
            return null;
        }
    }

    /**
     * This method will deserialize with json T type or ErrorResponseDto
     *
     * @param responseEntity
     * @return T
     */
    public T getResultActionObject(ResponseEntity<String> responseEntity, Class<T> t) {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = responseEntity.getBody();
        try {
            if (isError(responseEntity.getStatusCode())) {
                ErrorResponseDto errorDto = objectMapper.readValue(responseBody, ErrorResponseDto.class);
                if (errorDto != null) {
                    errorLogger.error(errorDto.getErrorMessage());
                } else {
                    errorLogger.error("Not know exception");
                }
            } else {
                return objectMapper.readValue(responseBody, t);
            }
            return null;
        } catch (IOException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
            return null;
        }
    }
}
