package com.example.enterprisebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IssueDTO {
    private Long id;
    
    @NotBlank
    private String title;
    
    private String description;
    
    @NotNull
    private Long projectId;
    
    private Long reporterId;
    private Long assigneeId;
    
    private String status;
    
    @NotBlank
    private String priority;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
