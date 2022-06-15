package com.gk.springbootskeleton.exceptionhandling;

import com.gk.springbootskeleton.exceptionhandling.exception.ExceptionFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Service errors.
 */
@Getter
@AllArgsConstructor
public enum ApiErrors implements ExceptionFactory {

    NOT_FOUND(HttpStatus.NOT_FOUND, CustomCode.NOT_FOUND);

    private HttpStatus status;
    private int code;

    private static class CustomCode {
        private static final int NOT_FOUND = 10404;
    }
}
