package com.waes.diffcheck.exception;

import lombok.Getter;

@Getter
public class DomainNotFoundException extends RuntimeException {
    private final String message;

    /**
     * @param message The detail of exception
     */
    public DomainNotFoundException(String message) {
        this.message = message;
    }
}