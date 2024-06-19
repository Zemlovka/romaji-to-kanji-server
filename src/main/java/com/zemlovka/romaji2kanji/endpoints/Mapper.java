package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.Word;
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
}
