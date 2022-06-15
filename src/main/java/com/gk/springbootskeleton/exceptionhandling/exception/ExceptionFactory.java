package com.gk.springbootskeleton.exceptionhandling.exception;


import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

/**
 * Used for creating and throwing {@link ServiceException}.
 * It is strongly recommended to use this factory with enum.
 * For example, the following code demonstrate preferable implementation:
 * <pre>
 *  enum SomeServiceExceptionFactory implements ExceptionFactory {
 *
 *      ID_PRESENTED_ON_CREATE(HttpStatus.BAD_REQUEST, 1000),
 *      USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, 1011),
 *      INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000);
 *
 *      private final HttpStatus status;
 *      private final int code;
 *
 *      ...constructor/getters...
 *  }
 * </pre>
 */
public interface ExceptionFactory {

    /**
     * The HTTP status that fits the exception (never {@code null}).
     */
    HttpStatus getStatus();

    /**
     * The code explaining the exception.
     * This code is optional.
     */
    default int getCode() {
        return 0;
    }

    /**
     * Allows to set HttpStatus code dynamically.
     *
     * @param status http status
     * @return current instance
     */
    default ExceptionFactory withStatus(HttpStatus status) {
        return this;
    }

    /**
     * Throws {@link ServiceException} according to enum instance context.
     *
     * @param reason exception message
     */
    default void throwEx(String reason) {
        throw newEx(reason);
    }

    /**
     * Throws {@link ServiceException} according to enum instance context.
     *
     * @param reason    the associated reason
     * @param throwable a nested exception
     */
    default void throwEx(String reason, Throwable throwable) {
        throw newEx(reason, throwable);
    }

    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reasonPattern message pattern describing exception reason
     * @param params        params that should be injected into #reasonPattern
     * @return the instance of exception without trowing it.
     */
    default void throwEx(String reasonPattern, Object... params) {
        throw newEx(reasonPattern, params);
    }

    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reasonPattern message pattern describing exception reason
     * @param throwable     a nested exception
     * @param params        params that should be injected into #reasonPattern
     * @return the instance of exception without trowing it.
     */
    default void throwEx(String reasonPattern, Throwable throwable, Object... params) {
        throw newEx(reasonPattern, throwable, params);
    }

    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reason reason the associated reason
     * @return the instance of exception without trowing it.
     */
    default ServiceException newEx(String reason) {
        return new ServiceException(getStatus(), getCode(), reason);
    }


    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reason    reason the associated reason
     * @param throwable a nested exception
     * @return the instance of exception without trowing it.
     */
    default ServiceException newEx(String reason, Throwable throwable) {
        return new ServiceException(getStatus(), getCode(), reason, throwable);
    }

    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reasonPattern message pattern describing exception reason
     * @param params        params that should be injected into #reasonPattern
     * @return the instance of exception without trowing it.
     */
    default ServiceException newEx(String reasonPattern, Object... params) {
        String reasonFormatted = MessageFormatter.arrayFormat(reasonPattern, params).getMessage();
        return new ServiceException(getStatus(), getCode(), reasonFormatted);
    }

    /**
     * Creates {@link ServiceException} according to enum instance context.
     *
     * @param reasonPattern message pattern describing exception reason
     * @param throwable     a nested exception
     * @param params        params that should be injected into #reasonPattern
     * @return the instance of exception without trowing it.
     */
    default ServiceException newEx(String reasonPattern, Throwable throwable, Object... params) {
        String reasonFormatted = MessageFormatter.arrayFormat(reasonPattern, params, throwable).getMessage();
        return new ServiceException(getStatus(), getCode(), reasonFormatted, throwable);
    }
}

