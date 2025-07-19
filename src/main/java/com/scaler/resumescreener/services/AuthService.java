package com.scaler.resumescreener.services;

import com.scaler.resumescreener.dto.request.LoginRequest;
import com.scaler.resumescreener.dto.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String register(RegisterRequest request) {
        // TODO: implement user registration logic
        return "User registered";
    }

    public String login(LoginRequest request) {
        // TODO: implement login and return JWT
        return "JWT Token";
    }
}
