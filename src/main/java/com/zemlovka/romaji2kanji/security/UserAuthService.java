package com.zemlovka.romaji2kanji.security;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserAuthService implements UserDetailsService {
    UserService userService;

    @Autowired
    public UserAuthService(UserService userService){
        this.userService = userService;
    }


    /**
     * Method allowing to authenticate user by username
     * @param username the username identifying the user whose data is required.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.loadUserByUsername(username);
    }
}
