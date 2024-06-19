package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


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

    public Optional<Word> getWord(int id) {
        return wordRepository.findById(id);
    }

    public Word insertWord(Word word) {
        if (word.getCreatedAt() == null) word.setCreatedAt(Instant.now());
        return wordRepository.save(word);
    }

    public void insertWords(List<Word> words) {
        wordRepository.saveAll(words);
    }
}
