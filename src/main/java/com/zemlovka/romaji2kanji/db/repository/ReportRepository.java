package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository<Report, Integer> {
}