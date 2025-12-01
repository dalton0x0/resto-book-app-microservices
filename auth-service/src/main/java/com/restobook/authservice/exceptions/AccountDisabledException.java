package com.restobook.authservice.exceptions;

import org.springframework.http.HttpStatus;

public class AccountDisabledException extends BusinessException {

    public AccountDisabledException() {
        super("Ce compte est désactivé", HttpStatus.FORBIDDEN, "ACCOUNT_DISABLED");
    }

    public AccountDisabledException(String message) {
        super(message, HttpStatus.FORBIDDEN, "ACCOUNT_DISABLED");
    }
}
