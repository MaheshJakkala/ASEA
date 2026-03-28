package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.dto.UserDTO;
import com.example.enterprisebackend.exception.EntityNotFoundException;
import com.example.enterprisebackend.mapper.UserMapper;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.repository.UserRepository;
import com.example.enterprisebackend.security.CustomUserDetails;
import com.example.enterprisebackend.service.AuditService;
import com.example.enterprisebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuditService auditService;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id, User deletedBy) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
        auditService.logAudit("User", id, Action.DELETE, deletedBy, "Deleted user: " + user.getUsername());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() 
                || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("Current logged in user not found"));
    }
}
