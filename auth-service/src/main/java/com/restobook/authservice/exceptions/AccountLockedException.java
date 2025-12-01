package com.restobook.authservice.exceptions;

import org.springframework.http.HttpStatus;

public class AccountLockedException extends BusinessException {

    public AccountLockedException() {
        super("Ce compte est verrouillé", HttpStatus.FORBIDDEN, "ACCOUNT_LOCKED");
    }

    public AccountLockedException(String message) {
        super(message, HttpStatus.FORBIDDEN, "ACCOUNT_LOCKED");
    }
}
