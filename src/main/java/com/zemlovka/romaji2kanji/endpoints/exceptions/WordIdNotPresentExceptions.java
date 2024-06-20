package com.zemlovka.romaji2kanji.endpoints.exceptions;

public class WordIdNotPresentExceptions extends RuntimeException {
    public WordIdNotPresentExceptions(String message) {
        super(message);
    }
}
