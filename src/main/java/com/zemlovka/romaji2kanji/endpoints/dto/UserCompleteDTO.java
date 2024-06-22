package com.zemlovka.romaji2kanji.endpoints.dto;

import java.time.Instant;
import java.util.List;


public record UserCompleteDTO(
        Integer id,
        String username,
        String role,
        Instant registeredAt,
        Instant updatedAt,
        List<WordDTO> createdWords,
        List<WordProgressDTO> wordProgresses,
        List<ReportDTOOut> reports,
//        List<AchievementProgress> achievementProgresses,
        List<FollowDTO> followers,
        List<FollowDTO> followingUsers

) {

}
