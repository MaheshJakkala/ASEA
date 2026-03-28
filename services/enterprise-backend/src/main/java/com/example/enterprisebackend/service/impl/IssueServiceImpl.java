package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.dto.IssueDTO;
import com.example.enterprisebackend.exception.EntityNotFoundException;
import com.example.enterprisebackend.mapper.IssueMapper;
import com.example.enterprisebackend.model.Issue;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.model.enums.IssueStatus;
import com.example.enterprisebackend.model.enums.Priority;
import com.example.enterprisebackend.repository.IssueRepository;
import com.example.enterprisebackend.repository.ProjectRepository;
import com.example.enterprisebackend.repository.UserRepository;
import com.example.enterprisebackend.service.AuditService;
import com.example.enterprisebackend.service.ExecutionLogService;
import com.example.enterprisebackend.service.IssueService;
import com.example.enterprisebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final IssueMapper issueMapper;
    private final UserService userService;
    private final AuditService auditService;
    private final ExecutionLogService executionLogService;

    @Override
    @Transactional
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Project project = projectRepository.findById(issueDTO.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        
        User currentUser = userService.getCurrentUser();
        
        User assignee = null;
        if (issueDTO.getAssigneeId() != null) {
            assignee = userRepository.findById(issueDTO.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee not found"));
        }

        Issue issue = Issue.builder()
                .title(issueDTO.getTitle())
                .description(issueDTO.getDescription())
                .project(project)
                .reporter(currentUser)
                .assignee(assignee)
                .status(IssueStatus.OPEN)
                .priority(Priority.valueOf(issueDTO.getPriority().toUpperCase()))
                .build();

        issue = issueRepository.save(issue);

        auditService.logAudit("Issue", issue.getId(), Action.CREATE, currentUser, "Issue created");
        executionLogService.logExecution(project, issue, Action.CREATE, currentUser, "Created issue " + issue.getTitle());

        return issueMapper.toDto(issue);
    }

    @Override
    public IssueDTO getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found with id: " + id));
        return issueMapper.toDto(issue);
    }

    @Override
    public Page<IssueDTO> getIssuesByProjectId(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        return issueRepository.findByProjectId(projectId, pageable)
                .map(issueMapper::toDto);
    }

    @Override
    @Transactional
    public IssueDTO updateIssueStatus(Long id, String status) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found with id: " + id));
        
        IssueStatus newStatus = IssueStatus.valueOf(status.toUpperCase());
        issue.setStatus(newStatus);
        
        issue = issueRepository.save(issue);

        User currentUser = userService.getCurrentUser();
        auditService.logAudit("Issue", issue.getId(), Action.STATUS_CHANGE, currentUser, "Status changed to " + status);
        executionLogService.logExecution(issue.getProject(), issue, Action.STATUS_CHANGE, currentUser, "Updated issue status to " + status);

        return issueMapper.toDto(issue);
    }

    @Override
    public Page<IssueDTO> getAllIssues(Pageable pageable) {
        return issueRepository.findAll(pageable).map(issueMapper::toDto);
    }
}
