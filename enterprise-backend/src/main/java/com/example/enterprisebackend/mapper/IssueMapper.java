package com.example.enterprisebackend.mapper;

import com.example.enterprisebackend.dto.IssueDTO;
import com.example.enterprisebackend.model.Issue;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public IssueDTO toDto(Issue issue) {
        if (issue == null) {
            return null;
        }

        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        
        if (issue.getStatus() != null) {
            dto.setStatus(issue.getStatus().name());
        }
        
        if (issue.getPriority() != null) {
            dto.setPriority(issue.getPriority().name());
        }

        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());

        if (issue.getProject() != null) {
            dto.setProjectId(issue.getProject().getId());
        }

        if (issue.getReporter() != null) {
            dto.setReporterId(issue.getReporter().getId());
        }

        if (issue.getAssignee() != null) {
            dto.setAssigneeId(issue.getAssignee().getId());
        }

        return dto;
    }
}
