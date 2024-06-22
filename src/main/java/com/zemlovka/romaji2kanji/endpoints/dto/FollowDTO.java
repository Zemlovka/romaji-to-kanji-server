package com.zemlovka.romaji2kanji.endpoints.dto;

import java.time.Instant;


public record FollowDTO(String followed, String following, Instant createdAt) {
}
