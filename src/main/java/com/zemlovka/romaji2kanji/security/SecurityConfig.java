package com.zemlovka.romaji2kanji.security;

import com.zemlovka.romaji2kanji.db.entity.Role;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;

import static org.springframework.security.config.Customizer.withDefaults;

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

    @EventListener(ApplicationReadyEvent.class)
    public void initDefaultUser() {
        if (!userService.userExists("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("password");
            user.setRole(Role.USER);
            user.setRegisteredAt(Instant.now());
            userService.createUser(user);
        }
        if (!userService.userExists("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("password");
            admin.setRole(Role.ADMIN);
            admin.setRegisteredAt(Instant.now());
            userService.createUser(admin);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

//        http.securityMatcher("/admin-page","/index","/contact","/register*");
        http.authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/users/all").hasRole(Role.ADMIN.name())
//                                .requestMatchers("/index").hasAnyRole("USER","ADMIN")
//                                .requestMatchers("/contact").permitAll()
                .requestMatchers("/users/**").permitAll()
                .anyRequest().authenticated())

                .httpBasic(httpSec -> httpSec.authenticationEntryPoint(unauthorizedEntrypoint)).userDetailsService(userAuthService);
        return http.build();
    }

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }

//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userService)
//                .passwordEncoder(encoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/api/**", "/auth/**").authenticated()
//                .mvcMatchers("/api/register", "/**").permitAll()
//                .and()
//                .csrf().disable()
//                .httpBasic()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
////                .loginProcessingUrl("/login")
////                .defaultSuccessUrl("/homepage.html", true)
////                .failureUrl("/login.html?error=true")
////                .failureHandler(authenticationFailureHandler())
////                .and()
////                .logout()
////                .logoutUrl("/perform_logout")
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessHandler(logoutSuccessHandler())
//        ;
//    }
}
