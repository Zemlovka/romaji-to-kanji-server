package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entitie.Word;
import com.zemlovka.romaji2kanji.db.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WordService {
    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
    public List<Word> getRandomWords(boolean isKatakana, boolean isHiragana, int number) {
        assert isKatakana || isHiragana;
        long qty = wordRepository.count();
        int lowerBound = (int)(Math.random() * qty) - number;
        int upperBound = lowerBound + number;
        List<Word> wordList;
        if (isKatakana && isHiragana)
            return wordRepository.findAllWordsByIdBetween(lowerBound, upperBound);
        else if (isKatakana)
            return wordRepository.findKatakanaWordsByIdBetween(lowerBound, upperBound);
        else
            return wordRepository.findHiraganaWordsByIdBetween(lowerBound, upperBound);
    }
}
