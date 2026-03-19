package com.storyfactory.architect.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "character_card")
public class CharacterCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "novel_id", nullable = false)
    private Long novelId;

    @Column(nullable = false)
    private String name;

    @Column(name = "role_type")
    private String roleType;

    @Column(columnDefinition = "TEXT")
    private String motivation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "personality_tags", columnDefinition = "json")
    private List<String> personalityTags;

    @Column(name = "hidden_secrets", columnDefinition = "TEXT")
    private String hiddenSecrets;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
