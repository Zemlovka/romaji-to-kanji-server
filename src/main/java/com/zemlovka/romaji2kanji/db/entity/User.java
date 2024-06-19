package com.zemlovka.romaji2kanji.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    private static final String ROLE_DELIMITER = "|";
    @Id
    @GeneratedValue()
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String roles;

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
    private List<AchievementProgress> achievmentsProgress = new ArrayList<>();

    @OneToMany(mappedBy = "followedUser")
    private List<Follow> followedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "followingUser")
    private List<Follow> followingUsers = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split("\\" + ROLE_DELIMITER)).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return Arrays.stream(roles.split("\\" + ROLE_DELIMITER)).toList();
    }

    public void setRoles(String... roles) {
        this.roles = String.join(ROLE_DELIMITER, roles);
    }
}
