package com.stockholdergame.server.web.dto;

public class ErrorBody {

    private String message;

    public ErrorBody(String message) {
        this.message = message;
    }

    public static ErrorBody of(String message) {
        return new ErrorBody(message);
    }

    public String getMessage() {
        return message;
    }
}
