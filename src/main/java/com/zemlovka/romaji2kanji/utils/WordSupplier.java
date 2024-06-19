package com.zemlovka.romaji2kanji.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.romaji2kanji.db.entity.Word;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


public class WordSupplier {
    private final static String WORDS_JSON = "words.json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<Word> getWordsPool() {
        List<WordJson> wordJsons;
        try {
            wordJsons = OBJECT_MAPPER.readValue(WordSupplier.class.getClassLoader().getResource(WORDS_JSON), new TypeReference<List<WordJson>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wordJsons.stream().map(WordSupplier::mapWord).collect(Collectors.toList());
    }

    public static Word mapWord(WordJson wordJson) {
        Word word = new Word();
        word.setEnglish(wordJson.mean);
        word.setKana(wordJson.jp.wd);
        word.setIsKatakana(wordJson.jp.isKatakana);
        word.setKanji(wordJson.jp.kj);
        word.setCreatedAt(Instant.now());
        return word;
    }

    @Getter
    @Setter
    static class WordJson {
        private String img;
        private String mean;
        private Jp jp;
        private String category;

    }

    @Getter
    @Setter
    static class Jp {
        private String wd;
        private String kj;
        private Boolean isKatakana;
    }
}
