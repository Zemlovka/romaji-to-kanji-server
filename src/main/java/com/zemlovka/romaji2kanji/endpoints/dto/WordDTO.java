package com.zemlovka.romaji2kanji.endpoints.dto;

public record WordDTO(
        Integer id,
        String english,
        String kana,
        String kanji,
        Boolean isKatakana,
        String createdBy
        )
{}