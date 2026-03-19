package com.storyfactory.architect.controller;

import com.storyfactory.architect.entity.OutlineNode;
import com.storyfactory.architect.repository.OutlineNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/architect/outlines")
public class OutlineNodeController {

    @Autowired
    private OutlineNodeRepository outlineNodeRepository;

    @PostMapping
    public OutlineNode createNode(@RequestBody OutlineNode node) {
        return outlineNodeRepository.save(node);
    }

    @GetMapping("/novel/{novelId}")
    public List<OutlineNode> getNodesByNovelId(@PathVariable Long novelId) {
        return outlineNodeRepository.findByNovelId(novelId);
    }

    @GetMapping("/parent/{parentId}")
    public List<OutlineNode> getNodesByParentId(@PathVariable Long parentId) {
        return outlineNodeRepository.findByParentIdOrderByOrderIndexAsc(parentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutlineNode> getNodeById(@PathVariable Long id) {
        return outlineNodeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutlineNode> updateNode(@PathVariable Long id, @RequestBody OutlineNode nodeDetails) {
        return outlineNodeRepository.findById(id)
                .map(node -> {
                    node.setTitle(nodeDetails.getTitle());
                    node.setSummary(nodeDetails.getSummary());
                    node.setNodeType(nodeDetails.getNodeType());
                    node.setOrderIndex(nodeDetails.getOrderIndex());
                    node.setContextSnapshot(nodeDetails.getContextSnapshot());
                    node.setStatus(nodeDetails.getStatus());
                    return ResponseEntity.ok(outlineNodeRepository.save(node));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        return outlineNodeRepository.findById(id)
                .map(node -> {
                    outlineNodeRepository.delete(node);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
