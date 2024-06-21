package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(int id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByPassword(String hash);
    boolean existsByUsername(String username);

    /**
     * Deletes user, returns if successful
     */
    boolean deleteByUsername(String username);

    @Query("select u from User u")
    List<User> getAll();
}