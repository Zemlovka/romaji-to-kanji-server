package com.zemlovka.romaji2kanji.db.entitie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "achievements_progress")
@IdClass(AchievmentProgressId.class)
@Getter
@Setter
public class AchievementProgress {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "achievement_id", referencedColumnName = "id")
    private Achievement achievement;

    @Column(nullable = false)
    private Instant achievedOn;
}
