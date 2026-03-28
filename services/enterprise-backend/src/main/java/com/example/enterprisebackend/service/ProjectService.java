package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO getProjectById(Long id);
    Page<ProjectDTO> getAllProjects(Pageable pageable);
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);
    void deleteProject(Long id);
}
