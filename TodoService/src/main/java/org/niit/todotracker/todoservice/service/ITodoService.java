package org.niit.todotracker.todoservice.service;

import org.niit.todotracker.todoservice.domain.Category;
import org.niit.todotracker.todoservice.domain.Todo;
import org.niit.todotracker.todoservice.domain.User;
import java.util.List;
import java.util.Set;
public interface ITodoService {
   //Method Register To User On MongoDB
   User registerUser(User user);
   //Method To Get All User
   List<User> getAllUsers();

   //Method to Adding Users Category Into A Set
   User addUsersCategoryToSet(Category category, String userEmail);

   //Method To add User's Todos Into A Set
   User addUsersTodoToSet(Todo todo, String userEmail);

   //Method To Get All The Categories Of User As A Set
   Set<Category> getAllCategoriesOfUser(String userEmail);

   //Method To Get All The Todos Of User As A Set
   Set<Todo> getAllTodosOfUser(String userEmail);

   //Method To Add Todos Into Category
   Category addTodosIntoCategories(Todo todo, String categoryName, String userEmail);

   //Method To Create Categories Of Any Todos
   Todo addCategoriesOfTodos(Category category, String todoTitle, String userEmail);

   //Method To Get User By E-Mail
   User searchUserByEmail(String emailId);

   //Method to Get Todos From Category
   Set<Todo> getTodosFromCategory(String userEmail, String categoryName);

   //Getting todos By Todos Id
   Todo getTodoByTodoId(String userEmail, String todoId, String categoryId);

   //Updating users Details
   User updateDetails(String userEmail, User user);

   //updating todos details
   Set<Todo> updateTodosDetails(String userEmail, Todo todo, String todoId);

   //delete todos
   boolean deleteTodos(String userEmail, String todoId);

   //delete Category
   boolean deleteCategory(String email,String categoryId);


   boolean deleteTodoFromCategory(String email,String todoId,String categoryName);

   int generateOtp();

   String verifyOtp(String email);

   User updatePassword(String email, User user);
}
