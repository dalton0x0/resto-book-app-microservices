package com.restobook.authservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException() {
        super("Email ou mot de passe incorrect", HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
    }

    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
    }
}
