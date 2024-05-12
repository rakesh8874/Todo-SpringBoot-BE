package org.niit.todotracker.todoservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    private String todoId;
    private String todoTitle;
    private String todoDesc;
    private String priorities;
    private LocalDateTime dueDate;
    private LocalDateTime reminderDate;
    private Set<Category> categorySet;
}
