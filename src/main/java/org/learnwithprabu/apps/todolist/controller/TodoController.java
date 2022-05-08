package org.learnwithprabu.apps.todolist.controller;

import org.learnwithprabu.apps.todolist.model.Todo;
import org.learnwithprabu.apps.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("todolist/v1")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(name = "Live status Endpoint", path = "/status")
    public String getLiveStatus() {
        return "ACTIVE";
    }

    @GetMapping(name = "Find all the todo list", path = "/todos")
    public List<Todo> getTodoList() {
        return todoService.findAllTodo();
    }

    @PostMapping(name = "Add new todo item", path = "/todos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo getTodoList(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    @DeleteMapping(name = "Delete a todo item", path = "/todos/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id) {
        if(id != null) {
            todoService.deleteTodo(id.longValue());
            return ResponseEntity.ok("Todo "+id+" deleted successfully");
        } else {
            return ResponseEntity.internalServerError().body("Todo "+id+" deleted successfully");
        }
    }
}
