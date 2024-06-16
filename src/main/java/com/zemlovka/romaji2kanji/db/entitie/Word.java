package com.zemlovka.romaji2kanji.db.entitie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "words")
@Getter
@Setter
public class Word {

    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String kana;

    @Column(nullable = false)
    private String kanji;

    @Column(nullable = false)
    private boolean isKatakana;

    @ManyToOne
    @JoinColumn
    private User createdBy;

    @Column
    private String status; //todo enum

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @OneToMany(mappedBy = "word")
    private List<SentenceWord> sentenceWords = new ArrayList<>();

    @OneToMany(mappedBy = "reportedWord")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "word")
    private List<WordProgress> wordsProgress = new ArrayList<>();

}
