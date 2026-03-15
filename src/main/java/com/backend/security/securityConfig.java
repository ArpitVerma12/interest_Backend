package com.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class securityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    
    
    @Autowired
    @Lazy
    public AuthTokenFilter authenticationJwtTokenFilter;
//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }
    
   

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//      http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        http.csrf(csrf -> csrf.disable());
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    
//    @Bean
//    public CommandLineRunner initData(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        return args -> {
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
//
//            UserDetails user1 = User.withUsername("user1")
//                    .password(passwordEncoder.encode("password1"))
//                    .authorities("USER")   // empty authorities
//                    .build();
//
//            UserDetails user2 = User.withUsername("user2")
//                    .password(passwordEncoder.encode("password2"))
//                    .authorities("USER")
//                    .build();
//
//            if (manager.userExists("user1")) manager.deleteUser("user1");
//            manager.createUser(user1);
//
//            if (manager.userExists("user2")) manager.deleteUser("user2");
//            manager.createUser(user2);
//        };
//    }
  

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}