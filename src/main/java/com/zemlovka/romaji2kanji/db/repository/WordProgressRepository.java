package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.entity.WordProgress;
import com.zemlovka.romaji2kanji.db.entity.WordProgressId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;


public interface WordProgressRepository extends JpaRepository<WordProgress, WordProgressId> {

    List<WordProgress> findWordProgressByUserAndAndWord(User user, Word word);
}