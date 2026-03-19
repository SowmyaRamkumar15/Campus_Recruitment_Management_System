package com.examly.springapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final OAuth2SuccessHandler successHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          OAuth2SuccessHandler successHandler,
                          CustomOAuth2UserService customOAuth2UserService) {
        this.jwtFilter = jwtFilter;
        this.successHandler = successHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ✅ Stateless (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
            )

            // ✅ JWT Filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            // ❌ Disable default login page
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // ✅ OAuth2 config
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(user -> user
                    .userService(customOAuth2UserService)
                )
                .successHandler(successHandler) // ⭐ VERY IMPORTANT
            );

        return http.build();
    }
}


// package com.examly.springapp.security;

// import org.springframework.context.annotation.*;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.*;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.cors.*;

// import java.util.List;

// @Configuration
// public class SecurityConfig {

//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//         this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
//      @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);
//         config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:5501"));
//         config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
//         config.setAllowedHeaders(List.of("Authorization","Content-Type"));
//         config.setExposedHeaders(List.of("Authorization"));

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);
//         return source;
//     }
// }