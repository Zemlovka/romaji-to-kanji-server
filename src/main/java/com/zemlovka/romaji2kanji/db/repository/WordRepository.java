package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.Word;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface WordRepository extends CrudRepository<Word, Integer> {

    long count();
    Optional<Word> findById(int id);

    @Query("select w from Word w where w.isKatakana = true and w.id >= ?1")
    List<Word> findKatakanaWordsByIdFrom(int lowerBound, Limit limit);

    @Query("select w from Word w where w.isKatakana = false and w.id >= ?1")
    List<Word> findHiraganaWordsByIdFrom(int lowerBound, Limit limit);

    @Query("select w from Word w where w.id >= ?1")
    List<Word> findAllWordsByIdFrom(int lowerBound, Limit limit);

    @Query("select w from Word w")
    List<Word> getAll();
}