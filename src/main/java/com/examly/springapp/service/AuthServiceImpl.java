package com.examly.springapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.LoginRequest;
import com.examly.springapp.dto.LoginResponse;
import com.examly.springapp.enums.Role;
import com.examly.springapp.exception.BadRequestException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.security.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null){
            user.setRole(Role.ROLE_USER);
        }
        logger.info("Registering user: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if(userOpt.isEmpty()){
            logger.warn("Login failed: User not found {}", request.getEmail());
            throw new ResourceNotFoundException("User not found with email: " + request.getEmail());
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            logger.warn("Login failed: Invalid password for {}", request.getEmail());
            throw new BadRequestException("Invalid password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        logger.info("User logged in: {}", request.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public String refreshToken(String refreshToken) {
        String email = jwtUtil.getEmailFromToken(refreshToken);
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            throw new BadRequestException("Invalid refresh token");
        }
        User user = userOpt.get();
        return jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
    }
}