package com.example.enterprisebackend.mapper;

import com.example.enterprisebackend.dto.ProjectDTO;
import com.example.enterprisebackend.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDTO toDto(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setRepoUrl(project.getRepoUrl());
        dto.setDescription(project.getDescription());
        dto.setCreatedAt(project.getCreatedAt());

        if (project.getOwner() != null) {
            dto.setOwnerId(project.getOwner().getId());
        }

        return dto;
    }
}
