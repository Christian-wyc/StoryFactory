package com.storyfactory.pipeline.domain;

import com.storyfactory.pipeline.PipelineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.Instant;

@Entity
@Table(name = "pipelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pipeline {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PipelineStatus status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userInput;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant completedAt;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}
