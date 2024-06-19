package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.WordProgress;
import com.zemlovka.romaji2kanji.db.entity.WordProgressId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WordProgressRepository extends JpaRepository<WordProgress, WordProgressId> {
}