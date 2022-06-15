package com.gk.springbootskeleton.exceptionhandling.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

/**
 * Generic exception which is thrown when a problem occurs during a work with Service.
 *
 * @see ServiceException
 */
@Getter
public final class ServiceException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = -22697239273124233L;
    private static final int DEFAULT_ERROR_CODE = 0;

    private final int errorCode;

    /**
     * Constructor a new {@code ServiceException} object.
     * @param status the HTTP status (required)
     * @param reason the associated reason (optional)
     */
    ServiceException(HttpStatus status, String reason) {
        this(status, DEFAULT_ERROR_CODE, reason);
    }

    /**
     * Constructor a new {@code ServiceException} object.
     * @param status the HTTP status (required)
     * @param reason the associated reason (optional)
     * @param cause a nested exception (optional)
     */
    ServiceException(HttpStatus status, String reason, Throwable cause) {
        this(status, DEFAULT_ERROR_CODE, reason, cause);
    }

    /**
     * Constructor a new {@code ServiceException} object.
     * @param status the HTTP status (required)
     * @param errorCode the error code representation of exception (optional)
     * @param reason the associated reason (optional)
     */
    ServiceException(HttpStatus status, Integer errorCode, String reason) {
        this(status, errorCode, reason, null);
    }

    /**
     * Constructor a new {@code ServiceException} object.
     * @param status the HTTP status (required)
     * @param errorCode the error code representation of exception (optional)
     * @param reason the associated reason (optional)
     * @param cause a nested exception (optional)
     */
    ServiceException(HttpStatus status, Integer errorCode, String reason, Throwable cause) {
        super(status, reason, cause);
        this.errorCode = errorCode;
    }
}
