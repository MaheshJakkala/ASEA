package com.example.enterprisebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    
    @NotBlank
    private String username;
    
    @NotBlank
    @Email
    private String email;
    
    private boolean enabled;
    private Set<String> roles;
}
