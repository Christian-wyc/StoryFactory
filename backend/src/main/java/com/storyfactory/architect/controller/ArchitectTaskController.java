package com.storyfactory.architect.controller;

import com.storyfactory.architect.entity.CharacterCard;
import com.storyfactory.architect.entity.GenerationTask;
import com.storyfactory.architect.entity.OutlineNode;
import com.storyfactory.architect.entity.WorldSetting;
import com.storyfactory.architect.service.AsyncTaskManager;
import com.storyfactory.architect.service.CharacterAgent;
import com.storyfactory.architect.service.OutlineEngine;
import com.storyfactory.architect.service.WorldBuilderAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/architect/tasks")
public class ArchitectTaskController {

    @Autowired
    private AsyncTaskManager taskManager;

    @Autowired
    private WorldBuilderAgent worldBuilderAgent;

    @Autowired
    private CharacterAgent characterAgent;

    @Autowired
    private OutlineEngine outlineEngine;

    @PostMapping("/world/{novelId}")
    public String buildWorld(@PathVariable Long novelId, @RequestParam String premise) {
        String taskId = taskManager.submitTask(novelId, "WORLD");
        taskManager.executeTask(taskId, () -> worldBuilderAgent.buildWorld(novelId, premise));
        return taskId;
    }

    @PostMapping("/characters/{novelId}")
    public String generateCharacters(@PathVariable Long novelId, @RequestParam String premise) {
        String taskId = taskManager.submitTask(novelId, "CHARACTER");
        taskManager.executeTask(taskId, () -> characterAgent.generateCharacters(novelId, premise));
        return taskId;
    }

    @PostMapping("/volumes/{novelId}")
    public String generateVolumes(@PathVariable Long novelId) {
        String taskId = taskManager.submitTask(novelId, "VOLUME");
        taskManager.executeTask(taskId, () -> outlineEngine.generateVolumes(novelId));
        return taskId;
    }

    @PostMapping("/chapters/{volumeNodeId}")
    public String splitChapters(@PathVariable Long volumeNodeId) {
        String taskId = taskManager.submitTask(null, "CHAPTER");
        taskManager.executeTask(taskId, () -> outlineEngine.splitToChapters(volumeNodeId));
        return taskId;
    }

    @PostMapping("/beats/{chapterNodeId}")
    public String splitBeats(@PathVariable Long chapterNodeId) {
        String taskId = taskManager.submitTask(null, "BEAT");
        taskManager.executeTask(taskId, () -> outlineEngine.splitToBeats(chapterNodeId));
        return taskId;
    }

    @GetMapping("/{taskId}")
    public GenerationTask getTaskStatus(@PathVariable String taskId) {
        return taskManager.getTaskStatus(taskId);
    }
}
