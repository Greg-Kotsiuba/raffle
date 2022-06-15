package com.gk.springbootskeleton.exceptionhandling.handler;


import brave.Tracer;
import com.gk.springbootskeleton.exceptionhandling.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice class created to handle exceptions globally.
 */
@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleResponseException(ServiceException e) {
        return toErrorResponse(e.getErrorCode(), e.getReason(), e.getStatus());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e) {
        return toErrorResponse(e.getStatus().value(), e.getReason(), e.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return toErrorResponse(httpStatus.value(), e.getMessage(), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request
    ) {
        super.handleExceptionInternal(e, body, headers, status, request);

        return toErrorResponse(status.value(), e.getMessage(), status);
    }

    private ResponseEntity<Object> toErrorResponse(Integer errorCode, String reason, HttpStatus status) {

        String traceId = getCurrentSpanTraceId();
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(traceId, errorCode, reason);

        return new ResponseEntity<>(apiErrorResponse, status);
    }

    private String getCurrentSpanTraceId() {
        return tracer.currentSpan().context().traceIdString();
    }

}