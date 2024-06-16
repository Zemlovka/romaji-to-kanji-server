package com.zemlovka.romaji2kanji.db.entitie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Instant registeredAt;

    @Column
    private Instant updatedAt;

    @OneToMany(mappedBy = "createdBy")
    private List<Word> createdWords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<WordProgress> wordProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "reportedBy")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AchievementProgress> achievmentsProgress = new ArrayList<>();

    @OneToMany(mappedBy = "followedUser")
    private List<Follow> followedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "followingUser")
    private List<Follow> followingUsers = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
