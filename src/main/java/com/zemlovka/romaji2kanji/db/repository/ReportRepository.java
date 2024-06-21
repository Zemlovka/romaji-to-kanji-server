package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.Report;
import com.zemlovka.romaji2kanji.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("select r from Report r")
    List<Report> getAll();
}