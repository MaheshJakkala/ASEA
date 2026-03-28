package com.example.enterprisebackend.model;

import com.example.enterprisebackend.model.enums.Action;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "execution_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executed_by", nullable = false)
    private User executedBy;

    @CreationTimestamp
    @Column(name = "executed_at", updatable = false)
    private LocalDateTime executedAt;
}
