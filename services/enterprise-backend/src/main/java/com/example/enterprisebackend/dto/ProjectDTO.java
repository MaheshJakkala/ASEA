package com.example.enterprisebackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProjectDTO {
    private Long id;
    
    @NotBlank
    private String name;
    
    private String repoUrl;
    private String description;
    
    // owner username or id, using username or ID is fine, let's use ID for DTO
    private Long ownerId;
    
    private LocalDateTime createdAt;
}
