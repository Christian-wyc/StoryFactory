package com.storyfactory.pipeline.repository;

import com.storyfactory.pipeline.PipelineStatus;
import com.storyfactory.pipeline.domain.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, String> {

    Optional<Pipeline> findByStatus(PipelineStatus status);

    @Query("UPDATE Pipeline p SET p.status = :status WHERE p.id = :id")
    @Modifying
    void updateStatus(@Param("id") String id, @Param("status") PipelineStatus status);
}
