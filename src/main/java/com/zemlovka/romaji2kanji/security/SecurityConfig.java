package com.zemlovka.romaji2kanji.security;

import com.zemlovka.romaji2kanji.db.entity.Role;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Instant;
import java.util.List;


@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "security.enabled" , havingValue = "true")
public class SecurityConfig {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private UnauthorizedEntrypoint unauthorizedEntrypoint;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${r2k.default.user.username}")
    private String userUsername;

    @Value("${r2k.default.user.password}")
    private String userPassword;

    @Value("${r2k.default.admin.username}")
    private String adminUsername;

    @Value("${r2k.default.admin.password}")
    private String adminPassword;

    /**
     * Initializing default users
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initDefaultUser() {
        if (!userService.userExists(userUsername)) {
            User user = new User();
            user.setUsername(userUsername);
            user.setPassword(userPassword);
            user.setRole(Role.ROLE_USER);
            user.setRegisteredAt(Instant.now());
            userService.createUser(user);
        }
        if (!userService.userExists(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(adminPassword);
            admin.setRole(Role.ROLE_ADMIN);
            admin.setRegisteredAt(Instant.now());
            userService.createUser(admin);
        }
    }

    /**
     * Filter chain separating admin panel from the rest of the site
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http
        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/users/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/words").permitAll()
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated())

                .httpBasic(httpSec -> httpSec.authenticationEntryPoint(unauthorizedEntrypoint)).userDetailsService(userAuthService);
        http.logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        return http.build();
    }

    /**
     * Cors cfg
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "set-cookie", "cookie", "Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "set-cookie", "cookie", "Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
