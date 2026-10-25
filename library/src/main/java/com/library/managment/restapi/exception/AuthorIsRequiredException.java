package com.library.managment.restapi.exception;

public class AuthorIsRequiredException extends RuntimeException {
    public AuthorIsRequiredException(String message) {
        super(message);
    }
}
