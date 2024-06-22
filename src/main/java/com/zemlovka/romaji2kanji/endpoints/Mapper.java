package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.*;
import com.zemlovka.romaji2kanji.endpoints.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public class Mapper {
    public static List<WordDTO> mapWord(List<Word> words) {
        return words.stream().map(Mapper::mapWord).toList();
    }

    public static WordDTO mapWord(Word word) {
        return new WordDTO(word.getId(), word.getEnglish(), word.getKana(), word.getKanji(), word.getIsKatakana(), word.getCreatedBy() == null ? "System" : word.getCreatedBy().getUsername(), word.getCreatedAt(), word.getUpdatedAt());
    }

    public static Word mapWord(WordDTO word) {
        Word response = new Word();
        response.setEnglish(word.english());
        response.setKana(word.kana());
        response.setKanji(word.kanji());
        response.setIsKatakana(word.isKatakana());
        return response;
    }

    public static User mapUser(NewUserDTO user) {
        User response = new User();
        response.setUsername(user.username());
        response.setPassword(user.password());
        return response;
    }

    public static UserDTO mapUser(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole().name(), user.getRegisteredAt(),
                user.getUpdatedAt());
    }

    public static List<UserDTO> mapUser(List<User> users) {
        return users.stream().map(Mapper::mapUser).toList();
    }

    public static WordProgressDTO mapWordProgress(WordProgress wordProgress) {
        return new WordProgressDTO(wordProgress.getUser().getUsername(), mapWord(wordProgress.getWord()), wordProgress.getTries(), wordProgress.isSuccessful(), wordProgress.getCreatedAt(), wordProgress.getUpdatedAt());
    }

    public static List<WordProgressDTO> mapWordProgress(List<WordProgress> wordProgress) {
        return wordProgress.stream().map(Mapper::mapWordProgress).toList();
    }

    public static ReportDTOOut mapReport(Report report) {
        return new ReportDTOOut(
                report.getId(),
                report.getReportedWord().getId(),
                report.getReportedWord().getKana(),
                report.getReportedWord().getEnglish(),
                report.getAppMode(),
                report.getCorrectVariant(),
                report.getNotes(),
                report.getCreatedAt(),
                report.getState()
        );
    }

    public static List<ReportDTOOut> mapReport(List<Report> reports) {
        return reports.stream().map(Mapper::mapReport).toList();
    }

    public static Report mapReport(ReportDTOIn reportDTOIn) {
        Report report = new Report();
        report.setReportedWordValue(reportDTOIn.reportedWord());
        report.setReportedWordRomajiValue(reportDTOIn.inputValue());
        report.setAppMode(reportDTOIn.appMode());
        report.setCorrectVariant(reportDTOIn.variant());
        report.setNotes(report.getNotes());
        return report;
    }

    public static UserCompleteDTO mapUserComplete(User user) {
        return new UserCompleteDTO(user.getId(), user.getUsername(), user.getRole().name(), user.getRegisteredAt(),
                user.getUpdatedAt(),
                mapWord(user.getCreatedWords()),
                mapWordProgress(user.getWordProgresses()),
                mapReport(user.getReports()),
                mapFollow(user.getFollowedUsers()),
                mapFollow(user.getFollowingUsers()));
    }

    public static FollowDTO mapFollow(Follow follow) {
        return new FollowDTO(follow.getFollowedUser().getUsername(), follow.getFollowingUser().getUsername(), follow.getCreatedAt());
    }

    public static List<FollowDTO> mapFollow(List<Follow> follows) {
        return follows.stream().map(Mapper::mapFollow).toList();
    }
}
