package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.UserDTO;
import com.example.enterprisebackend.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUser(Long id, User deletedBy);
    User getCurrentUser();
}
