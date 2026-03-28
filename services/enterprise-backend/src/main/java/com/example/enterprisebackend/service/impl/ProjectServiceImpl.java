package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.dto.ProjectDTO;
import com.example.enterprisebackend.exception.EntityNotFoundException;
import com.example.enterprisebackend.mapper.ProjectMapper;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.repository.ProjectRepository;
import com.example.enterprisebackend.service.AuditService;
import com.example.enterprisebackend.service.ExecutionLogService;
import com.example.enterprisebackend.service.ProjectService;
import com.example.enterprisebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final AuditService auditService;
    private final ExecutionLogService executionLogService;

    @Override
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        User currentUser = userService.getCurrentUser();

        Project project = Project.builder()
                .name(projectDTO.getName())
                .repoUrl(projectDTO.getRepoUrl())
                .description(projectDTO.getDescription())
                .owner(currentUser)
                .build();

        project = projectRepository.save(project);

        auditService.logAudit("Project", project.getId(), Action.CREATE, currentUser, "Project created: " + project.getName());
        executionLogService.logExecution(project, null, Action.CREATE, currentUser, "Created project " + project.getName());

        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
        return projectMapper.toDto(project);
    }

    @Override
    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(projectMapper::toDto);
    }

    @Override
    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        project.setName(projectDTO.getName());
        project.setRepoUrl(projectDTO.getRepoUrl());
        project.setDescription(projectDTO.getDescription());

        project = projectRepository.save(project);

        User currentUser = userService.getCurrentUser();
        auditService.logAudit("Project", project.getId(), Action.UPDATE, currentUser, "Project updated");
        
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        User currentUser = userService.getCurrentUser();
        
        auditService.logAudit("Project", project.getId(), Action.DELETE, currentUser, "Project deleted");
        executionLogService.logExecution(project, null, Action.DELETE, currentUser, "Deleted project " + project.getName());

        projectRepository.delete(project);
    }
}
