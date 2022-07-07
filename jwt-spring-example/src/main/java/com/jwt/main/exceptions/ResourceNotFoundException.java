package com.jwt.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message) {
        super(message);
        System.out.println(message);

    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
