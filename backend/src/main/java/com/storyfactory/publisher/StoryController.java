package com.storyfactory.publisher;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口/发布层
 * 暴露 RESTful API 给前端。
 */
@RestController
@RequestMapping("/api/story")
public class StoryController {

    @GetMapping("/health")
    public String health() {
        return "StoryFactory is running!";
    }
}
