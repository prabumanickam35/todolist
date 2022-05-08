package org.learnwithprabu.apps.todolist.repository;

import org.learnwithprabu.apps.todolist.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

}
