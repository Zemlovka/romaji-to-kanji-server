package com.zemlovka.romaji2kanji.db.service;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(userRepository.getAll());
        if (userRepository.existsByUsername(username)) {
            return userRepository.findUserByUsername(username);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void createUser(UserDetails user) {
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRoles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
        userEntity.setRegisteredAt(Instant.now());
        userRepository.save(userEntity);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRoles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
        userEntity.setRegisteredAt(Instant.now());
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
