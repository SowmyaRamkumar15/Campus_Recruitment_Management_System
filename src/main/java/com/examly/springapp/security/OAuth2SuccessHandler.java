package com.examly.springapp.security;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.enums.Role;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        String email = oAuthUser.getAttribute("email");
        String name = oAuthUser.getAttribute("name");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found from OAuth provider");
            return;
        }

        Optional<User> userOpt = userRepository.findByEmail(email);

        User user = userOpt.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name != null ? name : email);
            newUser.setRole(Role.ROLE_USER);
            return userRepository.save(newUser);
        });

        String token = jwtUtil.generateAccessToken(email, user.getRole().name());

        response.setContentType("application/json");
        response.getWriter().write(
            "{ \"status\": \"success\", \"token\": \"" + token + "\" }"
        );
    }
}