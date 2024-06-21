package com.zemlovka.romaji2kanji.endpoints.dto;

import java.time.Instant;


public record WordDTO(
        Integer id,
        String english,
        String kana,
        String kanji,
        Boolean isKatakana,
        String createdBy,
        Instant createdAt,
        Instant updatedAt
        )
{}