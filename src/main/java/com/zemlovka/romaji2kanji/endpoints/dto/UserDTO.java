package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.*;
import jakarta.persistence.OneToMany;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public record UserDTO(
        Integer id,
        String username,
        String role,
        Instant updatedAt,
        List<Word> createdWords,
        List<WordProgress> wordProgresses,
        List<Report> reports,
        List<AchievementProgress> achievementProgresses,
        List<Follow> followedUsers,
        List<Follow> followingUsers
) {
}
