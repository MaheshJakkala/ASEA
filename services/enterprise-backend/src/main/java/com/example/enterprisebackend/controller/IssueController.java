package com.example.enterprisebackend.controller;

import com.example.enterprisebackend.dto.IssueDTO;
import com.example.enterprisebackend.dto.response.IssueResponseDTO;
import com.example.enterprisebackend.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@Valid @RequestBody IssueDTO issueDTO) {
        return ResponseEntity.ok(new IssueResponseDTO(issueService.createIssue(issueDTO)));
    }

    @GetMapping
    public ResponseEntity<Page<IssueDTO>> getAllIssues(Pageable pageable) {
        return ResponseEntity.ok(issueService.getAllIssues(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDTO> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(new IssueResponseDTO(issueService.getIssueById(id)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueResponseDTO> updateIssueStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be blank");
        }
        return ResponseEntity.ok(new IssueResponseDTO(issueService.updateIssueStatus(id, status)));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Page<IssueDTO>> getIssuesByProjectId(@PathVariable Long projectId, Pageable pageable) {
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId, pageable));
    }
}
