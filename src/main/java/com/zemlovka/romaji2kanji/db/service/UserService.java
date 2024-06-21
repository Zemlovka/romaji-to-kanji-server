package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.Role;
import com.zemlovka.romaji2kanji.db.entity.User;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void updateUser(UserDetails user) {
//        User userEntity = new User();
//        userEntity.setUsername(user.getUsername());
//        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
//        //todo
//        userEntity.setRole(Role.ROLE_USER);
//        userEntity.setUpdatedAt(Instant.now());
//        userRepository.save(userEntity);
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
