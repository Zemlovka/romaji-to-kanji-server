package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.Word;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WordRepository extends CrudRepository<Word, Integer> {
    long count();

    @Query("select w from Word w where w.isKatakana = true and w.id >= ?1")
    List<Word> findKatakanaWordsByIdFrom(int lowerBound, Limit limit);

    @Query("select w from Word w where w.isKatakana = false and w.id >= ?1")
    List<Word> findHiraganaWordsByIdFrom(int lowerBound, Limit limit);

    @Query("select w from Word w where w.id >= ?1")
    List<Word> findAllWordsByIdFrom(int lowerBound, Limit limit);
}