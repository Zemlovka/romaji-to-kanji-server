package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.endpoints.dto.NewUserDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.UserDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.WordDTO;

import java.util.List;


public class Mapper {
    public static List<WordDTO> mapWord(List<Word> words) {
        return words.stream().map(Mapper::mapWord).toList();
    }

    public static WordDTO mapWord(Word word) {
        return new WordDTO(word.getId(), word.getEnglish(), word.getKana(), word.getKanji(), word.getIsKatakana());
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
        return new UserDTO(user.getId(), user.getUsername(), user.getRole().name(),
                user.getUpdatedAt(), user.getCreatedWords(), user.getWordProgresses(), user.getReports(),
                user.getAchievmentsProgress(), user.getFollowedUsers(), user.getFollowingUsers());
    }
}
