package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "sentence_words")
@IdClass(SentenceWordId.class)
@Getter
@Setter
public class SentenceWord {
    @Id
    @ManyToOne
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @Id
    @ManyToOne
    @JoinColumn(name = "sentence_id", referencedColumnName = "id")
    private Sentence sentence;

    @Id
    @Column(nullable = false)
    private int wordOrder;
}
