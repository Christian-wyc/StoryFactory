package com.storyfactory.architect.repository;

import com.storyfactory.architect.entity.GenerationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationTaskRepository extends JpaRepository<GenerationTask, String> {
}
