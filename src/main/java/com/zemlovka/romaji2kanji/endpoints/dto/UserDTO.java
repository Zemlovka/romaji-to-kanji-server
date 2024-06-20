package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.*;

import java.time.Instant;
import java.util.List;


public record UserDTO(
        Integer id,
        String username,
        String role,
        Instant updatedAt
) {
}
