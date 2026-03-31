package com.storyfactory.pipeline.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.Instant;

@Entity
@Table(name = "pipeline_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PipelineEvent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "pipeline_id", nullable = false)
    private String pipelineId;

    @Column(name = "module_name", nullable = false, length = 50)
    private String moduleName;

    @Column(name = "event_type", nullable = false, length = 20)
    private String eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = createdAt != null ? createdAt : Instant.now();
    }
}
