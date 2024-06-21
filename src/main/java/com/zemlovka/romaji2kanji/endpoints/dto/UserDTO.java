package com.zemlovka.romaji2kanji.endpoints.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public record UserDTO(
        Integer id,
        String username,
        String role,
        Instant registeredAt,
        Instant updatedAt
) {
}
