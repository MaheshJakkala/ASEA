package com.example.enterprisebackend.service;

import com.example.enterprisebackend.model.Issue;
import com.example.enterprisebackend.model.Project;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;

public interface ExecutionLogService {
    void logExecution(Project project, Issue issue, Action action, User executedBy, String detail);
}
