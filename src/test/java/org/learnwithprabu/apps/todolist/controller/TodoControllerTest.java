package org.learnwithprabu.apps.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learnwithprabu.apps.todolist.helper.TodoDataFactory;
import org.learnwithprabu.apps.todolist.model.Todo;
import org.learnwithprabu.apps.todolist.service.TodoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        when(todoService.findAllTodo()).thenReturn(TodoDataFactory.getGeneratedTodoList(2));
        when(todoService.saveTodo(any(Todo.class))).thenReturn(TodoDataFactory.getGeneratedTodoList(1).get(0));
        doNothing().when(todoService).deleteTodo(anyLong());
    }

    @Test
    @DisplayName("When attempt to check the status then should return default status Active")
    public void testLiveStatusWhenCalledItShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/todolist/v1/status"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("ACTIVE"));
    }

    @Test
    @DisplayName("When attempt to get all todo items then should return all the list")
    public void testGetAllTodoList() throws Exception {
        mockMvc.perform(get("/todolist/v1/todos"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].title", Matchers.startsWith("Sample Title"))
                );
    }

    @Test
    @DisplayName("When attempt to add new todo then should add new todo and return success")
    public void testAddTodo() throws Exception {
        mockMvc.perform(post("/todolist/v1/todos")
                        .content(objectMapper.writeValueAsBytes(TodoDataFactory.getGeneratedTodoList(1).get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.title", Matchers.startsWith("Sample Title")),
                        jsonPath("$.description", Matchers.startsWith("Sample description"))
                );
    }

    @Test
    @DisplayName("When attempt to delete a todo then should remove the item from the database")
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/todolist/v1/todos/1")
                ).andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @DisplayName("When attempt to delete a todo but forgot to add the id then should return an error")
    public void testDeleteTodoIncorrectId() throws Exception {
        mockMvc.perform(delete("/todolist/v1/todos/ ")
                ).andDo(print())
                .andExpectAll(
                        status().isInternalServerError()
                );
    }
}
