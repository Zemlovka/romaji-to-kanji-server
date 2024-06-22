package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.ReportAppMode;
import com.zemlovka.romaji2kanji.db.entity.ReportState;

import java.time.Instant;


public record ReportDTOOut(
        Integer id,
        Integer reportedWordId,
        String reportedWord,
        String inputValue,
        ReportAppMode appMode,
        String variant,
        String notes,
        Instant createdAt,
        ReportState state
) {
}
