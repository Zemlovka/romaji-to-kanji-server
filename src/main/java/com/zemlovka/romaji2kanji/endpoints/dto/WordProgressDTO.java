package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;


public record WordProgressDTO(
        String username,
        WordDTO word,
        Integer tries,
        boolean successful,
        Instant createdAt,
        Instant updatedAt
) {
}
