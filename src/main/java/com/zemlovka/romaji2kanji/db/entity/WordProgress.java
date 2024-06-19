package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


/**
 * Many to Many entity with parameters for connecting words and users
 */
@Entity
@Table(name = "words_progress")
@IdClass(WordProgressId.class)
@Getter
@Setter
public class WordProgress {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @Id
    @Column(nullable = false)
    private Integer tries;

    @Column(nullable = false)
    private boolean successful;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;


}
