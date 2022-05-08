package org.learnwithprabu.apps.todolist.helper;

import org.learnwithprabu.apps.todolist.model.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoDataFactory {

        public static List<Todo> getGeneratedTodoList(int count) {
            List<Todo> todoList = new ArrayList<>();
            while(count>0) {
                todoList.add(new Todo((long)count, "Sample description "+count, "Sample Title "+count, LocalDateTime.now()));
                count--;
            }

            return todoList;
        }

}
