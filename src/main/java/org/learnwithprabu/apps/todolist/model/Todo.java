package org.learnwithprabu.apps.todolist.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    public Todo(long id, String description, String title, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private String description;

    @NonNull
    private String title;

    private LocalDateTime dueDate;

    private LocalDateTime updatedDate;

}
