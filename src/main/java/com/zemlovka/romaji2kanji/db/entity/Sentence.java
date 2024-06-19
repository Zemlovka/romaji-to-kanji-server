package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sentences")
@Getter
@Setter
public class Sentence {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private SentenceTemplate template;

    @OneToMany(mappedBy = "sentence")
    private List<SentenceWord> sentenceWords = new ArrayList<>();
}
