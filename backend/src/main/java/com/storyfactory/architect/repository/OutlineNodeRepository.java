package com.storyfactory.architect.repository;

import com.storyfactory.architect.entity.OutlineNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutlineNodeRepository extends JpaRepository<OutlineNode, Long> {
    List<OutlineNode> findByNovelId(Long novelId);
    List<OutlineNode> findByParentIdOrderByOrderIndexAsc(Long parentId);
}
