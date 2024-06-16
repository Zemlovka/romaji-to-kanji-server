package com.zemlovka.romaji2kanji.endpoints.dto;

public record WordDTO(
        int id,
        String english,
        String kana,
        String kanji,
        boolean isKatakana
        )
{}