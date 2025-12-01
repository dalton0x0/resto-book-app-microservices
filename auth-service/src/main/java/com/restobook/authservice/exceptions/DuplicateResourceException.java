package com.restobook.authservice.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s existe déjà avec %s: '%s'", resourceName, fieldName, fieldValue),
                HttpStatus.CONFLICT,
                "DUPLICATE_RESOURCE"
        );
    }

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }
}
