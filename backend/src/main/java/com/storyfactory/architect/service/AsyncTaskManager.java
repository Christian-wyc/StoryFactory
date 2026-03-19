package com.storyfactory.architect.service;

import com.storyfactory.architect.entity.GenerationTask;
import com.storyfactory.architect.repository.GenerationTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
public class AsyncTaskManager {

    @Autowired
    private GenerationTaskRepository taskRepository;

    public String submitTask(Long novelId, String type) {
        String taskId = UUID.randomUUID().toString();
        GenerationTask task = new GenerationTask();
        task.setId(taskId);
        task.setNovelId(novelId);
        task.setType(type);
        taskRepository.save(task);
        return taskId;
    }

    @Async
    public void executeTask(String taskId, Supplier<Object> taskAction) {
        GenerationTask task = taskRepository.findById(taskId).orElseThrow();
        try {
            Object result = taskAction.get();
            task.setStatus("COMPLETED");
            // 这里可以根据返回结果设置 resultId，如果是列表则设置第一个或汇总
            taskRepository.save(task);
        } catch (Exception e) {
            task.setStatus("FAILED");
            task.setErrorMessage(e.getMessage());
            taskRepository.save(task);
        }
    }

    public GenerationTask getTaskStatus(String taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
}
