package com.example.enterprisebackend.controller;

import com.example.enterprisebackend.dto.request.AuthRequest;
import com.example.enterprisebackend.dto.request.RegisterRequest;
import com.example.enterprisebackend.dto.response.AuthResponse;
import com.example.enterprisebackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.authenticateUser(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }
}
