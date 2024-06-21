package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.*;
import com.zemlovka.romaji2kanji.db.repository.ReportRepository;
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
    private final ReportRepository reportRepository;

    public WordService(WordRepository wordRepository, WordProgressRepository wordProgressRepository, ReportRepository reportRepository) {
        this.wordRepository = wordRepository;
        this.wordProgressRepository = wordProgressRepository;
        this.reportRepository = reportRepository;
    }

    public List<Word> getAllWords() {
        return wordRepository.getAll();
    }

    public List<Report> getAllReports() {
        return reportRepository.getAll();
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

    public Word updateWord(Word word) {
        if (!wordRepository.existsById(word.getId()))
            throw new IllegalArgumentException("Trying to edit word that does not exist");
        Word existingWord = wordRepository.findById(word.getId()).orElseThrow(
                () -> new IllegalArgumentException("Trying to edit word that does not exist"));
        existingWord.setUpdatedAt(Instant.now());
        if (word.getKana() != null)
            existingWord.setKana(word.getKana());
        if (word.getKanji() != null)
            existingWord.setKanji(word.getKanji());
        if (word.getEnglish() != null)
            existingWord.setEnglish(word.getEnglish());
        if (word.getIsKatakana() != null)
            existingWord.setIsKatakana(word.getIsKatakana());
        existingWord.setUpdatedAt(Instant.now());
        return wordRepository.save(existingWord);
    }

    public void deleteWord(Integer wordId) {
        wordRepository.deleteById(wordId);
    }

    public Report saveReport(Report report, Word word, User user) {
        report.setReportedWord(word);
        report.setReportedBy(user);
        report.setCreatedAt(Instant.now());
        report.setState(ReportState.NEW);
        return reportRepository.save(report);
    }

    public Report updateReport(Integer reportId, ReportState newReportState, String notes) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Report with this id does not exist"));
        report.setState(newReportState);
        report.setNotes(notes);
        report.setResolvedAtAt(Instant.now());
        reportRepository.save(report);
        return reportRepository.save(report);
    }

    public void deleteReport(Integer reportId) {
        reportRepository.deleteById(reportId);
    }
}
