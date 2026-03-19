package com.examly.springapp.security;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.RevokedTokenRepository;
import com.examly.springapp.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RevokedTokenRepository revokedTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   UserRepository userRepository,
                                   RevokedTokenRepository revokedTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                if (jwtUtil.isTokenExpired(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return;
                }

                if (revokedTokenRepository.existsByToken(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token revoked");
                    return;
                }

                String email = jwtUtil.getEmailFromToken(token);
                String role = jwtUtil.getRoleFromToken(token);

                Optional<User> user = userRepository.findByEmail(email);

                if (user.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(role)
                                    )
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

// package com.examly.springapp.security;

// import com.examly.springapp.model.User;
// import com.examly.springapp.repository.RevokedTokenRepository;
// import com.examly.springapp.repository.UserRepository;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;
// import java.util.Collections;
// import java.util.Optional;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtUtil jwtUtil;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private RevokedTokenRepository revokedTokenRepository;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain chain)
//             throws ServletException, IOException {

//         String header = request.getHeader("Authorization");

//         if (header != null && header.startsWith("Bearer ")) {

//             String token = header.substring(7);

//             if (jwtUtil.isTokenExpired(token)) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("Token expired");
//                 return;
//             }

//             if (revokedTokenRepository.existsByToken(token)) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("Token revoked");
//                 return;
//             }

//             String email = jwtUtil.getEmailFromToken(token);
//             String role = jwtUtil.getRoleFromToken(token);

//             Optional<User> user = userRepository.findByEmail(email);

//             if (user.isPresent()) {

//                 UsernamePasswordAuthenticationToken authentication =
//                         new UsernamePasswordAuthenticationToken(
//                                 email,
//                                 null,
//                                 Collections.singletonList(
//                                         new SimpleGrantedAuthority(role)
//                                 )
//                         );

//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }
//         }

//         chain.doFilter(request, response);
//     }
// }