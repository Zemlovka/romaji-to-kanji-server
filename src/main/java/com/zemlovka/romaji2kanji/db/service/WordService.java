package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.entity.WordProgress;
import com.zemlovka.romaji2kanji.db.repository.WordProgressRepository;
import com.zemlovka.romaji2kanji.db.repository.WordRepository;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordProgressRepository wordProgressRepository;

    public WordService(WordRepository wordRepository, WordProgressRepository wordProgressRepository) {
        this.wordRepository = wordRepository;
        this.wordProgressRepository = wordProgressRepository;
    }

    public List<Word> getAllWords() {
        return wordRepository.getAll();
    }

    public List<Word> getRandomWords(boolean isKatakana, boolean isHiragana, int number) {
        assert isKatakana || isHiragana;
        long qty = wordRepository.count() - number;
        int lowerBound = (int)(Math.random() * qty);
        List<Word> words;
        if (isKatakana && isHiragana)
            words = wordRepository.findAllWordsByIdFrom(lowerBound, Limit.of(number));
        else if (isKatakana)
            words = wordRepository.findKatakanaWordsByIdFrom(lowerBound, Limit.of(number));
        else
            words = wordRepository.findHiraganaWordsByIdFrom(lowerBound, Limit.of(number));
        Collections.shuffle(words);
        return words;
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
    public void insertWordProgress(WordProgress wordProgress) {
        List<WordProgress> wordProgressList = wordProgressRepository.findWordProgressByUserAndAndWord(wordProgress.getUser(), wordProgress.getWord());
        int maxTry;
        if (wordProgressList.size() > 0)
            maxTry = Collections.max(wordProgressList.stream().map(WordProgress::getTries).toList()) + 1;
        else
            maxTry = 1;
        wordProgress.setTries(maxTry);
        wordProgress.setCreatedAt(Instant.now());
        wordProgress.setUpdatedAt(Instant.now());
        wordProgressRepository.save(wordProgress);
    }
}
