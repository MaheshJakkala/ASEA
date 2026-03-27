package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.request.AuthRequest;
import com.example.enterprisebackend.dto.request.RegisterRequest;
import com.example.enterprisebackend.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticateUser(AuthRequest authRequest);
    void registerUser(RegisterRequest registerRequest);
}
