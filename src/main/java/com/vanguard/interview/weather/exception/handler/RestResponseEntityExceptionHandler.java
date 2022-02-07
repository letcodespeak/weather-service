package com.vanguard.interview.weather.exception.handler;

import com.vanguard.interview.weather.exception.ApiKeyNotAllowedException;
import com.vanguard.interview.weather.exception.QuotaExceededException;
import com.vanguard.interview.weather.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildApiError(ex.getMessage(),HttpStatus.NOT_FOUND.value()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildApiError(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { ApiKeyNotAllowedException.class })
    protected ResponseEntity<Object> handleNotAuthorized(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildApiError(ex.getMessage(),HttpStatus.UNAUTHORIZED.value()),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value
            = { QuotaExceededException.class })
    protected ResponseEntity<Object> handleForbidden(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildApiError(ex.getMessage(),HttpStatus.FORBIDDEN.value()),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value
            = { RestClientException.class })
    protected ResponseEntity<Object> handleInternalServerError(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR.value()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ApiError buildApiError(String errorMessage,int code){
        return new ApiError(code,errorMessage);
    }

}
