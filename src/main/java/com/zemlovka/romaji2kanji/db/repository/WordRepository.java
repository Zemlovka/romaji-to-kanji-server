package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entitie.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface WordRepository extends JpaRepository<Word, Integer> {
    long count();

    @Query("select w from Word w where w.isKatakana = true and w.id >= ?2 and w.id < ?3")
    List<Word> findKatakanaWordsByIdBetween(int lowerBound, int upperBound);

    @Query("select w from Word w where w.isKatakana = false and w.id >= ?2 and w.id < ?3")
    List<Word> findHiraganaWordsByIdBetween(int lowerBound, int upperBound);

    @Query("select w from Word w where w.id >= ?2 and w.id < ?3")
    List<Word> findAllWordsByIdBetween(int lowerBound, int upperBound);
}