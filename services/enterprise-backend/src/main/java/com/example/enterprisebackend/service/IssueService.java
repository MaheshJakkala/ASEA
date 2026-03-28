package com.example.enterprisebackend.service;

import com.example.enterprisebackend.dto.IssueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueService {
    IssueDTO createIssue(IssueDTO issueDTO);
    IssueDTO getIssueById(Long id);
    Page<IssueDTO> getIssuesByProjectId(Long projectId, Pageable pageable);
    IssueDTO updateIssueStatus(Long id, String status);
    Page<IssueDTO> getAllIssues(Pageable pageable);
}
