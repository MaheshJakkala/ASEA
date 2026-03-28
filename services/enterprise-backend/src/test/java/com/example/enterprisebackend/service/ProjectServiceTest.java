package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.ProjectDTO;
import com.example.enterprisebackend.mapper.ProjectMapper;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.repository.ProjectRepository;
import com.example.enterprisebackend.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private UserService userService;

    @Mock
    private AuditService auditService;

    @Mock
    private ExecutionLogService executionLogService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDTO projectDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("testuser").build();
        project = Project.builder().id(1L).name("Test Project").owner(user).build();
        
        projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Test Project");
    }

    @Test
    void testGetProjectById() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
    }

    @Test
    void testCreateProject() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDTO);

        ProjectDTO requestDTO = new ProjectDTO();
        requestDTO.setName("Test Project");

        ProjectDTO result = projectService.createProject(requestDTO);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        verify(projectRepository).save(any(Project.class));
        verify(auditService).logAudit(any(), any(), any(), any(), any());
    }
}
