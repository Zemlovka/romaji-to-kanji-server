package com.zemlovka.romaji2kanji.db.entitie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
@Getter
@Setter
public class Follow {
    @Id
    @ManyToOne
    @JoinColumn(name = "following_user_id", referencedColumnName = "id")
    private User followingUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed_user_id", referencedColumnName = "id")
    private User followedUser;

    @Column(nullable = false)
    private Instant createdAt;
}
