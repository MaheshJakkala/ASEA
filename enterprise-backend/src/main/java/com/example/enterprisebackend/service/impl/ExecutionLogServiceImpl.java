package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.model.ExecutionLog;
import com.example.enterprisebackend.model.Issue;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.repository.ExecutionLogRepository;
import com.example.enterprisebackend.service.ExecutionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionLogServiceImpl implements ExecutionLogService {

    private final ExecutionLogRepository executionLogRepository;

    @Override
    public void logExecution(Project project, Issue issue, Action action, User executedBy, String detail) {
        ExecutionLog log = ExecutionLog.builder()
                .project(project)
                .issue(issue)
                .action(action)
                .executedBy(executedBy)
                .detail(detail)
                .build();
        executionLogRepository.save(log);
    }
}
