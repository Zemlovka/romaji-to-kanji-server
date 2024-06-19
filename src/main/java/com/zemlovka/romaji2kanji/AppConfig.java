package com.zemlovka.romaji2kanji;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.utils.WordSupplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.Instant;


@EnableJpaRepositories
@Configuration
public class AppConfig {
    @PersistenceContext
    EntityManager em;

    private final WordService wordService;

    @Autowired
    public AppConfig(WordService wordService) {
        this.wordService = wordService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateWords() {
        if (wordService.getRandomWords(true,true, 1).size() == 0)
            wordService.insertWords(WordSupplier.getWordsPool());
        System.out.println(wordService.getRandomWords(true, true, 10));
    }

//    @Bean
//    public UserDetailsService users() {
//        // The builder will ensure the passwords are encoded before saving in memory
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        UserDetails user = users
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = users
//                .username("admin")
//                .password("password")
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
