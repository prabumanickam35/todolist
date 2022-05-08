package org.learnwithprabu.apps.todolist.service;

import org.learnwithprabu.apps.todolist.model.Todo;
import org.learnwithprabu.apps.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public List<Todo> findAllTodo() {
        return (List<Todo>) todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo) {
        todo.setUpdatedDate(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    public void deleteTodo(long todoId) {
        todoRepository.deleteById(todoId);
    }
}
