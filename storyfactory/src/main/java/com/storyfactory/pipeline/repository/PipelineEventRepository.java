package com.storyfactory.pipeline.repository;

import com.storyfactory.pipeline.domain.PipelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PipelineEventRepository extends JpaRepository<PipelineEvent, String> {

    List<PipelineEvent> findByPipelineIdOrderByCreatedAtAsc(String pipelineId);

    List<PipelineEvent> findByPipelineIdAndModuleNameOrderByCreatedAtAsc(String pipelineId, String moduleName);
}
