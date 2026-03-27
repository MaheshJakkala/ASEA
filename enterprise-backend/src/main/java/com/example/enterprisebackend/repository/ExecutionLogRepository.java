package com.example.enterprisebackend.repository;

import com.example.enterprisebackend.model.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {
}
