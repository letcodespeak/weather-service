package com.vanguard.interview.weather.exception;

public class ApiKeyNotAllowedException extends RuntimeException {

    public ApiKeyNotAllowedException() {
        super();
    }

    public ApiKeyNotAllowedException(String message) {
        super(message);
    }

    public ApiKeyNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

}
