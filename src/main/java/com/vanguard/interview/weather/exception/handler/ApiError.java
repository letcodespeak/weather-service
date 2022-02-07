package com.vanguard.interview.weather.exception.handler;

public class ApiError {

    public ApiError(int code, String message){
        this.code = code;
        this.message = message;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
