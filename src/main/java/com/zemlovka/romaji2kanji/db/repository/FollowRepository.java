package com.zemlovka.romaji2kanji.db.repository;

import com.zemlovka.romaji2kanji.db.entity.Follow;
import com.zemlovka.romaji2kanji.db.entity.FollowId;
import com.zemlovka.romaji2kanji.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findAllByFollowedUser(User followedUser);
    List<Follow> findAllByFollowingUser(User followingUser);
    void deleteByFollowedUserAndFollowingUser(User followedUser, User followingUser);
}