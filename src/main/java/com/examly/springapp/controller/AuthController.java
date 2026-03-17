package com.examly.springapp.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.LoginRequest;
import com.examly.springapp.dto.LoginResponse;
import com.examly.springapp.model.RevokedToken;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.RevokedTokenRepository;
import com.examly.springapp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        return ResponseEntity.ok(authService.register(user));

    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));

    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            revokedTokenRepository.save(new RevokedToken(token));
        }

        return ResponseEntity.ok("Logged out successfully");
    }

    // REFRESH TOKEN
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody String refreshToken) {

        return ResponseEntity.ok(authService.refreshToken(refreshToken));

    }
}