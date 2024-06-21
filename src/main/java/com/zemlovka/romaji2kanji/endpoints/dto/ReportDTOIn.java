package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.ReportAppMode;


public record ReportDTOIn(
        Integer reportedWordId,
        String reportedWord,
        String inputValue,
        ReportAppMode appMode,
        String variant,
        String notes
) {
}
