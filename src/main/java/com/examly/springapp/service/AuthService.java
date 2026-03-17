package com.examly.springapp.service;

import com.examly.springapp.dto.LoginRequest;
import com.examly.springapp.dto.LoginResponse;
import com.examly.springapp.model.User;

public interface AuthService 
{

    User register(User user);

    LoginResponse login(LoginRequest request);

    String refreshToken(String refreshToken);
}