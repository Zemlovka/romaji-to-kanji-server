package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "achievements")
@Getter
@Setter
public class Achievement {
    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String requirements; //todo logical expression

    @OneToMany(mappedBy = "achievement")
    private List<AchievementProgress> achievmentsProgress = new ArrayList<>();
}
