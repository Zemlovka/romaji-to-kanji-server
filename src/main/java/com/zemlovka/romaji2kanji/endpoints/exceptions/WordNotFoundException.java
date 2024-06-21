package com.zemlovka.romaji2kanji.endpoints.exceptions;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
