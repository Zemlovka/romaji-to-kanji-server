package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.Role;


public record UpdateUserDTO(
        String username,
        Role role) {
}
