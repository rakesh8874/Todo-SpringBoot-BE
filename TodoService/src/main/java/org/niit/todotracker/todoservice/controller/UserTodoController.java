package org.niit.todotracker.todoservice.controller;

import io.jsonwebtoken.Claims;
import org.niit.todotracker.todoservice.domain.Category;
import org.niit.todotracker.todoservice.domain.Todo;
import org.niit.todotracker.todoservice.domain.User;
import org.niit.todotracker.todoservice.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/userTodo")
public class UserTodoController {

   private final ITodoService todoService;

   @Autowired
    public UserTodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    //Handler Method of Post Request To Register User In MongoDb

    @PostMapping("/register")
      public ResponseEntity<?>register(@RequestBody User user){
        return new ResponseEntity<>(todoService.registerUser(user),HttpStatus.CREATED);
       }

   //Handler Method Of Get Request To Get All The User
    @GetMapping("/allUser")
    public ResponseEntity<List<User>> getAllUsers(){
       return new ResponseEntity<>(todoService.getAllUsers(), HttpStatus.OK);
    }


    //Handler Method Of Post Request To Add Categories of User
    @PostMapping("/addCategory")
    public ResponseEntity<User> addUsersCategoryToList(@RequestBody Category category, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.addUsersCategoryToSet(category,userEmail), HttpStatus.OK);
    }

    //Handler Method Of Post Request To Add Todos Of User
    @PostMapping("/addTodo")
    public ResponseEntity<User> addUsersTodoToList(@RequestBody Todo todo, HttpServletRequest request){
       Claims claims = (Claims) request.getAttribute("claims");
       String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.addUsersTodoToSet(todo,userEmail), HttpStatus.OK);
    }

    //Handler Method Of Get Request To  Get Categories Of User
    @GetMapping("/categories")
    public ResponseEntity<Set<Category>> getCategoriesFromList(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.getAllCategoriesOfUser(userEmail), HttpStatus.OK);
    }

    //Handler Method Of Get Request To Get Todos Of User

    @GetMapping("/todos")
    public ResponseEntity<Set<Todo>> getTodosFromList(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        return new ResponseEntity<>(todoService.getAllTodosOfUser(userEmail), HttpStatus.OK);
    }

    //Handler Method To Handle Post Request to Add Todos into Categories
    @PostMapping("/addTodoIntoCategory/{categoryName}")
    public ResponseEntity<Category> addTodosIntoCategory(@RequestBody Todo todo, @PathVariable String categoryName, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.addTodosIntoCategories(todo, categoryName, userEmail), HttpStatus.OK);
    }

    //Handler Method To Handle Post Request To Add Todos Category
    @PostMapping("/addTodosCategory/{todoTitle}")
    public ResponseEntity<Todo> addCategoryOfTodos(@RequestBody Category category, @PathVariable String todoTitle, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.addCategoriesOfTodos(category, todoTitle, userEmail), HttpStatus.OK);
    }

    //Handler Method To Handle Get Request To Get User By Email
    @GetMapping("/current-user")
    public ResponseEntity<User> getUserId(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.searchUserByEmail(userEmail), HttpStatus.OK);
    }

    //Handler Method To Handle Get Request To Get Todos From Category

    @GetMapping("/getTodosOfCategory/{categoryId}")
    public ResponseEntity<Set<Todo>> getTodosOfCategory(@PathVariable String categoryId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        return new ResponseEntity<>(todoService.getTodosFromCategory(userEmail,categoryId), HttpStatus.OK);
    }
    //Handler Method to Handle Get Request To Get Individual Todos from set

    @GetMapping("/getSingleTodo/{todoId}/{categoryName}")
    public ResponseEntity<Todo> getTodoAsPerId(@PathVariable String todoId, @PathVariable String categoryName, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.getTodoByTodoId(userEmail, todoId, categoryName), HttpStatus.OK);
    }

    //Handler Method to Handle Put Request To Update users details
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUsersDetails(@RequestBody User user, HttpServletRequest request){
       Claims claims = (Claims) request.getAttribute("claims");
       String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.updateDetails(userEmail, user),HttpStatus.CREATED);
    }

    //Handler Method to Handle Put Request To Update todos details
    @PutMapping("/updateTodo/{todoId}")
    public ResponseEntity<Set<Todo>> updateTodosDetails(@RequestBody Todo todo, @PathVariable String todoId, HttpServletRequest request){
       Claims claims = (Claims) request.getAttribute("claims");
       String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.updateTodosDetails(userEmail, todo, todoId), HttpStatus.CREATED);
    }
    //Handler Method to Handle Delete Request To Delete Todos
    @DeleteMapping("/deleteTodo/{todoId}")
    public ResponseEntity<Boolean> deleteTodos(@PathVariable String todoId, HttpServletRequest request){
       Claims claims = (Claims) request.getAttribute("claims");
       String userEmail = claims.getSubject();
       return new ResponseEntity<>(todoService.deleteTodos(userEmail, todoId),HttpStatus.OK);
    }

    //handler method to handle delete request to delete category
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable String categoryId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        return new ResponseEntity<>(todoService.deleteCategory(userEmail, categoryId),HttpStatus.OK);
    }

    //Handler method to handle delete request to delete todos of category
    @DeleteMapping("/deleteTodoFromCategory/{todoId}/{categoryName}")
    public ResponseEntity<Boolean> deleteTodoFromCategory(@PathVariable String categoryName,@PathVariable String todoId, HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String userEmail = claims.getSubject();
        return new ResponseEntity<>(todoService.deleteTodoFromCategory(userEmail, todoId, categoryName),HttpStatus.OK);
    }

    @GetMapping("/otp/{emailId}")
    public ResponseEntity<?>getOtp(@PathVariable String emailId)
    {
        return new ResponseEntity<>(todoService.verifyOtp(emailId),HttpStatus.CREATED);
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<?>updatePassword(@RequestBody User user,@PathVariable String emailId)
    {
        return new ResponseEntity<>(todoService.updatePassword(emailId,user),HttpStatus.OK);
    }


}
