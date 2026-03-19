package com.storyfactory.architect.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "generation_task")
public class GenerationTask {
    @Id
    private String id; // TaskID (UUID)

    private String type; // WORLD, CHARACTER, VOLUME, CHAPTER, BEAT

    private Long novelId;

    private String status = "PROCESSING"; // PROCESSING, COMPLETED, FAILED

    private String resultId; // 成功后关联的结果 ID (如卷 ID)

    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
