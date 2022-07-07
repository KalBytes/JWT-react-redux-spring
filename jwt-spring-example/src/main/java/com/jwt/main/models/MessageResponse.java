package com.jwt.main.models;

public class MessageResponse {
    private String message;

    public MessageResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
