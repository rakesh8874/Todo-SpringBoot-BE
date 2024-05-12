package com.niit.ArchiveService.service;

import com.niit.ArchiveService.domain.Todo;
import com.niit.ArchiveService.domain.User;
import java.util.List;

public interface IArchiveService {
    User registerUser(User user);
    List<Todo> getArchivedTodoOfUser(String email);
    List<Todo> getCompletedTodoOfUser(String email);
    boolean deleteTodo(String email,String todoId);
    User addUsersTodoToListArchived(Todo todo, String email);
    User addUsersTodoToListCompleted(Todo todo, String email);

}
