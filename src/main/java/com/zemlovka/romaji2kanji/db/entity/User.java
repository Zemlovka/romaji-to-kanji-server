package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    private static final String ROLE_DELIMITER = "|";
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Instant registeredAt;

    @Column(nullable = false)
    private String password;

    @Column
    private Instant updatedAt;

    @OneToMany(mappedBy = "createdBy")
    private List<Word> createdWords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<WordProgress> wordProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "reportedBy")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AchievementProgress> achievementProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "followedUser")
    private List<Follow> followedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "followingUser")
    private List<Follow> followingUsers = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null)
            return null;
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
