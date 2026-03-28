package com.example.enterprisebackend.service.impl;

import com.example.enterprisebackend.model.AuditEntry;
import com.example.enterprisebackend.model.User;
import com.example.enterprisebackend.model.enums.Action;
import com.example.enterprisebackend.repository.AuditEntryRepository;
import com.example.enterprisebackend.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditEntryRepository auditEntryRepository;

    @Override
    public void logAudit(String entityName, Long entityId, Action action, User performedBy, String detail) {
        AuditEntry entry = AuditEntry.builder()
                .entity(entityName)
                .entityId(entityId)
                .action(action)
                .performedBy(performedBy)
                .detail(detail)
                .build();
        auditEntryRepository.save(entry);
    }
}
