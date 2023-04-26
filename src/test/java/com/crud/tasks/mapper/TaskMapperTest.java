package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void mapToTaskTest() {
        // given
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        // when
        Task task = taskMapper.mapToTask(taskDto);

        // then
        assertNotNull(task);
        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getContent(), task.getContent());
    }

    @Test
    void mapToTaskDtoTest() {
        // given
        Task task = new Task(1L, "Title", "Content");

        // when
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // then
        assertNotNull(taskDto);
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    void mapToTaskDtoListTest() {
        // given
        List<Task> taskList = List.of(
                new Task(1L, "Title 1", "Content 1"),
                new Task(2L, "Title 2", "Content 2"),
                new Task(3L, "Title 3", "Content 3")
        );

        // when
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        // then
        assertNotNull(taskDtoList);
        assertEquals(taskList.size(), taskDtoList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            TaskDto taskDto = taskDtoList.get(i);
            assertEquals(task.getId(), taskDto.getId());
            assertEquals(task.getTitle(), taskDto.getTitle());
            assertEquals(task.getContent(), taskDto.getContent());
        }
    }
}
