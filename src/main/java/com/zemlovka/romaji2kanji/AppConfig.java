package com.zemlovka.romaji2kanji;

import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.utils.WordSupplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;


@EnableJpaRepositories
@Configuration
public class AppConfig {
    @PersistenceContext
    EntityManager em;

    private final WordService wordService;

    @Autowired
    public AppConfig(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * Populating the words db if empty
     */
    @EventListener(ApplicationReadyEvent.class)
    public void populateWords() {
        if (wordService.count() == 0)
            wordService.insertWords(WordSupplier.getWordsPool());
    }
}
