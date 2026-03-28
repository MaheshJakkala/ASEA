package com.example.enterprisebackend.controller;

import com.example.enterprisebackend.dto.UserDTO;
import com.example.enterprisebackend.dto.response.UserResponseDTO;
import com.example.enterprisebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserResponseDTO(userService.getUserById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id, userService.getCurrentUser());
        return ResponseEntity.ok("User deleted successfully");
    }
}
