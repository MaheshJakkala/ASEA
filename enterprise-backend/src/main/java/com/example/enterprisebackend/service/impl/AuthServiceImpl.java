package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.dto.request.AuthRequest;
import com.example.enterprisebackend.dto.request.RegisterRequest;
import com.example.enterprisebackend.dto.response.AuthResponse;
import com.example.enterprisebackend.exception.CustomValidationException;
import com.example.enterprisebackend.exception.EntityNotFoundException;
import com.example.enterprisebackend.model.Role;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.model.enums.RoleName;
import com.example.enterprisebackend.repository.RoleRepository;
import com.example.enterprisebackend.repository.UserRepository;
import com.example.enterprisebackend.security.JwtUtil;
import com.example.enterprisebackend.service.AuditService;
import com.example.enterprisebackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuditService auditService;

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(authentication);

            User user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);
            if (user != null) {
                auditService.logAudit("User", user.getId(), Action.LOGIN, user, "User logged in successfully");
                log.info("User logged in: {}", user.getUsername());
            }

            return new AuthResponse(jwt);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            User user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);
            if (user != null) {
                auditService.logAudit("User", user.getId(), Action.LOGIN_FAILED, user, "Failed login attempt");
            }
            throw e; // Let global handler pick it up
        }
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new CustomValidationException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new CustomValidationException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .enabled(true)
                .roles(new HashSet<>())
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        log.info("User registered successfully: {}", user.getUsername());
        auditService.logAudit("User", user.getId(), Action.CREATE, user, "User registered");
    }
}
