package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn
    private User reportedBy;

    @ManyToOne(optional = false)
    @JoinColumn
    private Word reportedWord;

    @Column(nullable = false)
    private String appMode; //todo enum

    @Column(nullable = false)
    private String correctVariant;

    @Column
    private String notes;

    @Column
    private String state; //todo enum

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant resolvedAtAt;

}
