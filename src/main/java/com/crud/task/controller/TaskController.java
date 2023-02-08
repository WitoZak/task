package com.crud.task.controller;

import com.crud.task.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @GetMapping("")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return  new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {

    }

    @PatchMapping("/{taskId}")
    public TaskDto updateTask(@PathVariable("taskId") TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");


    }

    @PostMapping("")
    public void createTask(TaskDto taskDto) {

    }
}
