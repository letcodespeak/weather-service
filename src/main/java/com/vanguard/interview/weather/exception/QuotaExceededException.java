package com.vanguard.interview.weather.exception;

public class QuotaExceededException extends RuntimeException{
    public QuotaExceededException() {
        super();
    }

    public QuotaExceededException(String message) {
        super(message);
    }

    public QuotaExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
