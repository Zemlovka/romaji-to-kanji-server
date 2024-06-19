package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sentence_templates")
@Getter
@Setter
public class SentenceTemplate {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    //todo length
    private String text;

    @Column(nullable = false)
    private String gaps;

    @OneToMany(mappedBy = "template")
    private List<Sentence> sentences = new ArrayList<>();


}
