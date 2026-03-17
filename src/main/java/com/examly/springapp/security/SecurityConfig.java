// package com.examly.springapp.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableMethodSecurity
// public class SecurityConfig {

//     private final JwtAuthenticationFilter jwtFilter;
//     private final OAuth2SuccessHandler successHandler;
//     private final CustomOAuth2UserService customOAuth2UserService;

//     public SecurityConfig(JwtAuthenticationFilter jwtFilter,
//                           OAuth2SuccessHandler successHandler,
//                           CustomOAuth2UserService customOAuth2UserService) {
//         this.jwtFilter = jwtFilter;
//         this.successHandler = successHandler;
//         this.customOAuth2UserService = customOAuth2UserService;
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//         http
//             .csrf(csrf -> csrf.disable())

//             .sessionManagement(session ->
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//             )

//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api/auth/**").permitAll()
//                 .requestMatchers("/oauth2/**").permitAll()
//                 .requestMatchers("/login/**").permitAll()
//                 .anyRequest().authenticated()
//             )

//             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

//             .formLogin(form -> form.disable())
//             .httpBasic(basic -> basic.disable())

//             .oauth2Login(oauth -> oauth
//                 .userInfoEndpoint(user -> user.userService(customOAuth2UserService))
//                 .successHandler(successHandler)
//             );

//         return http.build();
//     }
// }


package com.examly.springapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig 
{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth

        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()

        .requestMatchers("/api/departments/**").hasAuthority("ROLE_ADMIN")
        .requestMatchers("/api/job-positions/**").hasAuthority("ROLE_ADMIN")

        .requestMatchers("/api/job-applications/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")

        .requestMatchers("/api/interview-feedbacks/**").authenticated()

        .anyRequest().authenticated()

        )
        .addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://127.0.0.1:5501"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}