package com.niit.ArchiveService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo {
    @Id
    private String todoId;
    private String todoTitle;
    private String todoDesc;
    private String priorities;
    private LocalDateTime dueDate;
    private String completed;
    private String archived;
    private LocalDateTime reminderDate;
}
