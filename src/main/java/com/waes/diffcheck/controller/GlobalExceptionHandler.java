package com.waes.diffcheck.controller;

import com.waes.diffcheck.domain.response.ErrorDto;
import com.waes.diffcheck.exception.DomainNotFoundException;
import com.waes.diffcheck.exception.InvalidJsonFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles InvalidJsonFormatException and creates error message
     *
     * @param exception InvalidJsonFormatException
     * @return HTTP Bad Request(400) with related error message
     */
    @ExceptionHandler(InvalidJsonFormatException.class)
    public ResponseEntity<ErrorDto> handleInvalidJsonException(InvalidJsonFormatException exception) {
        LOGGER.error("InvalidJsonFormatException occurred with message: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DomainNotFoundException and creates error message
     *
     * @param exception DomainNotFoundException
     * @return HTTP Not Found(404) with related error message
     */
    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<ErrorDto> handleDomainNotFoundException(DomainNotFoundException exception) {
        LOGGER.error("DomainNotFoundException occurred with message: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles IllegalArgumentException and creates error message
     *
     * @param exception IllegalArgumentException
     * @return HTTP Bad Request(400) with related error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException exception) {
        LOGGER.error("IllegalArgumentException occurred with message: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}