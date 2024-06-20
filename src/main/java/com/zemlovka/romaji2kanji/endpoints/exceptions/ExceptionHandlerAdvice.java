package com.zemlovka.romaji2kanji.endpoints.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleException(UserAlreadyExistsException e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}