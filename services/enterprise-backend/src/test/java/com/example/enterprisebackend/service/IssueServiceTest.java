package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.IssueDTO;
import com.example.enterprisebackend.mapper.IssueMapper;
import com.example.enterprisebackend.model.Issue;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.IssueStatus;
import com.example.enterprisebackend.repository.IssueRepository;
import com.example.enterprisebackend.service.impl.IssueServiceImpl;
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
class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueMapper issueMapper;
    
    @Mock
    private UserService userService;

    @Mock
    private AuditService auditService;
    
    @Mock
    private ExecutionLogService executionLogService;

    @InjectMocks
    private IssueServiceImpl issueService;

    private Issue issue;
    private IssueDTO issueDTO;
    private User user;
    private Project project;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).username("testuser").build();
        project = Project.builder().id(1L).name("Test Project").build();
        issue = Issue.builder().id(1L).title("Test Issue").status(IssueStatus.OPEN).project(project).build();
        
        issueDTO = new IssueDTO();
        issueDTO.setId(1L);
        issueDTO.setTitle("Test Issue");
        issueDTO.setStatus("OPEN");
    }

    @Test
    void testGetIssueById() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(issueMapper.toDto(issue)).thenReturn(issueDTO);

        IssueDTO result = issueService.getIssueById(1L);

        assertNotNull(result);
        assertEquals("Test Issue", result.getTitle());
    }

    @Test
    void testUpdateIssueStatus() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(userService.getCurrentUser()).thenReturn(user);
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);
        
        IssueDTO updatedDTO = new IssueDTO();
        updatedDTO.setStatus("IN_PROGRESS");
        when(issueMapper.toDto(any(Issue.class))).thenReturn(updatedDTO);

        IssueDTO result = issueService.updateIssueStatus(1L, "IN_PROGRESS");

        assertNotNull(result);
        assertEquals("IN_PROGRESS", result.getStatus());
        verify(auditService).logAudit(any(), any(), any(), any(), any());
    }
}
