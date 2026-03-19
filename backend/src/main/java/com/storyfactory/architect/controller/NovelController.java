package com.storyfactory.architect.controller;

import com.storyfactory.architect.entity.Novel;
import com.storyfactory.architect.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/architect/novels")
public class NovelController {

    @Autowired
    private NovelRepository novelRepository;

    @PostMapping
    public Novel createNovel(@RequestBody Novel novel) {
        return novelRepository.save(novel);
    }

    @GetMapping
    public List<Novel> getAllNovels() {
        return novelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Novel> getNovelById(@PathVariable Long id) {
        return novelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Novel> updateNovel(@PathVariable Long id, @RequestBody Novel novelDetails) {
        return novelRepository.findById(id)
                .map(novel -> {
                    novel.setTitle(novelDetails.getTitle());
                    novel.setGenre(novelDetails.getGenre());
                    novel.setPremise(novelDetails.getPremise());
                    novel.setStatus(novelDetails.getStatus());
                    return ResponseEntity.ok(novelRepository.save(novel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNovel(@PathVariable Long id) {
        return novelRepository.findById(id)
                .map(novel -> {
                    novelRepository.delete(novel);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
