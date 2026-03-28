package com.example.enterprisebackend.service;

import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;

public interface AuditService {
    void logAudit(String entityName, Long entityId, Action action, User performedBy, String detail);
}
