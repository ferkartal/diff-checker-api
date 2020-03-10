package com.waes.diffcheck.exception;

import lombok.Getter;

@Getter
public class InvalidJsonFormatException extends RuntimeException {
    private final String message;

    /**
     * @param message The detail of exception
     */
    public InvalidJsonFormatException(String message) {
        this.message = message;
    }
}