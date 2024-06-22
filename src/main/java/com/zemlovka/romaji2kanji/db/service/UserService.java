package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.Follow;
import com.zemlovka.romaji2kanji.db.entity.FollowId;
import com.zemlovka.romaji2kanji.db.entity.Role;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.repository.FollowRepository;
import com.zemlovka.romaji2kanji.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, FollowRepository followRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Follow followUser(User following, String followedUsername) {
        Follow follow = new Follow();
        follow.setFollowedUser(loadUserByUsername(followedUsername));
        follow.setFollowingUser(following);
        follow.setCreatedAt(Instant.now());
        return followRepository.save(follow);
    }

    public void unfollow(User unfollowing, String followedUsername) {
        followRepository.deleteByFollowedUserAndFollowingUser(loadUserByUsername(followedUsername), unfollowing);
    }

    public List<Follow> getFollowing(User user) {
        return followRepository.findAllByFollowingUser(user);
    }

    public List<Follow> getFollowers(User user) {
        return followRepository.findAllByFollowedUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User loadUserById(int id) throws UsernameNotFoundException {
        return userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void createUser(UserDetails user) {
        if (userExists(user.getUsername()))
            throw new RuntimeException("User already exists");
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setRegisteredAt(Instant.now());
        userRepository.save(userEntity);
    }

    public User createUser(User user) {
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty())
            user.setRole(Role.ROLE_USER);
        if (user.getRegisteredAt() == null)
            user.setRegisteredAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User updateUserRole(String username, Role role) {
        User user = userRepository.findUserByUsername(username). orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole(role);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findUserByPassword(passwordEncoder.encode(oldPassword)). orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
