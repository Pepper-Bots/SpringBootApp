package com.hrizzon.demo2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    protected PasswordEncoder passwordEncoder; // Je récupère le passwordEncoder de AuthController et DemoApplication
    protected UserDetailsService userDetailsService;
    protected JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder); // comment j'ai encodé le mdp de la personne ?
        auth.setUserDetailsService(userDetailsService); // comment j'ai récupéré une personne ?
        return auth;
    }

    @Bean
    public SecurityFilterChain configureAuthentification(HttpSecurity http) throws Exception {

        return http
                .csrf(c -> c.disable()) // Désactiver les failles CSRF (création de token de formulaire) Les failles CSRF sont des failles de sécurité qui consiste à faire cliquer un lien à qqun qui a des droits supérieurs contre son gré. Ici, nous ne sommes pas concernés car le lien authentication est un token -> indiqué avec disable()
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Désactivation des sessions - on n'utilise pas les cookies
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
