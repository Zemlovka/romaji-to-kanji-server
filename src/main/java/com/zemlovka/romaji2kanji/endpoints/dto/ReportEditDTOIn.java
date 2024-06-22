package com.zemlovka.romaji2kanji.endpoints.dto;

import com.zemlovka.romaji2kanji.db.entity.ReportState;


public record ReportEditDTOIn(
        Integer id,
        ReportState state,
        String notes

) {
}
