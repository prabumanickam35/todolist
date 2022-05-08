package org.learnwithprabu.apps.todolist.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learnwithprabu.apps.todolist.model.Todo;
import org.learnwithprabu.apps.todolist.repository.TodoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    List<Todo> result = new ArrayList<>();

    @BeforeEach
    public void init() {
        result.addAll(Arrays.asList(
                new Todo[] {
                        new Todo(1l, "Sample description", "Sample Title", LocalDateTime.now()),
                        new Todo(2l, "Sample description 1", "Sample Title 1", LocalDateTime.now()),
                }));
        when(todoRepository.findAll()).thenReturn(result);

        when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(result.get(0));

        doNothing().when(todoRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("When find all todo service attempts to get the list then should return default empty list")
    public void testFindAllTodoList() throws Exception {
        List<Todo> todoList = todoService.findAllTodo();
        assertTrue(todoList.size() >= 2);
    }

    @Test
    @DisplayName("When new todo is passed then should save and return the saved todo item")
    public void testSaveNewTodoItem() throws Exception {
        Todo newTodo = result.get(0);
        Todo savedTodo = todoService.saveTodo(newTodo);

        assertAll(
                () -> assertNotNull(savedTodo, "Todo should not be a null"),
                () -> assertTrue(savedTodo.getId() > 0, "Todo item should have an generated id"),
                () -> assertEquals(savedTodo.getTitle(), newTodo.getTitle(), "Todo title should match"),
                () -> assertEquals(savedTodo.getDescription(), newTodo.getDescription(), "Todo description should match"),
                () -> assertEquals(savedTodo.getDueDate(), newTodo.getDueDate(), "Todo due date should match"),
                () -> assertTrue(savedTodo.getUpdatedDate().isBefore(LocalDateTime.now()), "Todo updated date should be before current date and time")
        );
    }

    @Test
    @DisplayName("When attempted to delete a todo item then deletes silently")
    public void testDeleteTodoItem() throws Exception {
        Todo savedTodo = result.get(0);
        todoService.deleteTodo(savedTodo.getId());
    }
}
