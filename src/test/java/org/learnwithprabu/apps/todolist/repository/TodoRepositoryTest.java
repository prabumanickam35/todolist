package org.learnwithprabu.apps.todolist.repository;

import org.junit.jupiter.api.*;
import org.learnwithprabu.apps.todolist.model.Todo;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    Todo todo1;

    @BeforeEach
    public void init() {
        todo1 = new Todo();
        todo1.setTitle("Personal checklist");
        todo1.setDescription("Welcome to the world of action items");
        todo1.setDueDate(LocalDateTime.now());
        todo1 = todoRepository.save(todo1);
    }

    @AfterEach
    public void tearDown() {
        todoRepository.deleteAllById(Collections.singletonList(todo1.getId()));
    }

    @Test
    @DisplayName("when attempt to get all todo list while data available on the database should return non-empty list ")
    public void testGetFindAllTodolistForNonEmptyList() {
        List<Todo> todoList = (List<Todo>) todoRepository.findAll();
        assertTrue(todoList.size() > 0, "Todo list is more than zero");
    }

    @Test
    @DisplayName("when attempt to save a todo item then it should save an return with an valid id")
    public void testSaveTodoItem() {
        Todo todoLocal = new Todo();
        todoLocal.setTitle("Sample Title");
        todoLocal.setDescription("Sample description");
        todoLocal.setDueDate(LocalDateTime.now());
        Todo savedTodo = todoRepository.save(todoLocal);
        assertAll(
                () -> assertNotNull(savedTodo, "Todo should not be a null"),
                () -> assertTrue(savedTodo.getId() > 0, "Todo item should have an generated id"),
                () -> assertEquals(savedTodo.getTitle(), todoLocal.getTitle(), "Todo title should match"),
                () -> assertEquals(savedTodo.getDescription(), todoLocal.getDescription(), "Todo description should match"),
                () -> assertEquals(savedTodo.getDueDate(), todoLocal.getDueDate(), "Todo due date should match")
        );

    }

    @Test
    @DisplayName("when attempt to delete a todo item then it should delete and return the deleted item")
    public void testDeleteTodoItemById() throws Exception {

        Todo todoLocal = new Todo();
        todoLocal.setTitle("Sample Title");
        todoLocal.setDescription("Sample description");
        todoLocal.setDueDate(LocalDateTime.now());
        Todo savedTodo = todoRepository.save(todoLocal);
        assertAll(
                () -> assertNotNull(savedTodo, "Todo should not be a null"),
                () -> assertTrue(savedTodo.getId() > 0, "Todo item should have an generated id"),
                () -> assertEquals(savedTodo.getTitle(), todoLocal.getTitle(), "Todo title should match"),
                () -> assertEquals(savedTodo.getDescription(), todoLocal.getDescription(), "Todo description should match"),
                () -> assertEquals(savedTodo.getDueDate(), todoLocal.getDueDate(), "Todo due date should match")
        );

        todoRepository.deleteById(savedTodo.getId());
        Optional<Todo> deletedTodo = todoRepository.findById(savedTodo.getId());
        assertTrue(deletedTodo.isEmpty(),"The deleted item should not be available");

    }
}
