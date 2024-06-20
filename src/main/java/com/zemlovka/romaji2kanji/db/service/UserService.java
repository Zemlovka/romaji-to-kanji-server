package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.Role;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class UserService implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User loadUserById(int id) throws UsernameNotFoundException {
        if (userRepository.existsById(id)) {
            return userRepository.findUserById(id);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findUserByUsername(username);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername()))
            throw new RuntimeException("User already exists");
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setRegisteredAt(Instant.now());
        userRepository.save(userEntity);
    }

    public User createUser(User user) {
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty())
            user.setRole(Role.USER);
        if (user.getRegisteredAt() == null)
            user.setRegisteredAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        //todo
        userEntity.setRole(Role.USER);
        userEntity.setUpdatedAt(Instant.now());
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findUserByPassword(passwordEncoder.encode(oldPassword));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
