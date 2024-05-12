package org.niit.todotracker.todoservice.service;

import org.niit.todotracker.todoservice.domain.Category;
import org.niit.todotracker.todoservice.domain.Todo;
import org.niit.todotracker.todoservice.domain.User;
import org.niit.todotracker.todoservice.domain.UserDto;
import org.niit.todotracker.todoservice.exceptions.ResourceAlreadyExistException;
import org.niit.todotracker.todoservice.exceptions.ResourceNotFoundException;
import org.niit.todotracker.todoservice.proxy.ArchiveProxy;
import org.niit.todotracker.todoservice.proxy.UserProxy;
import org.niit.todotracker.todoservice.rabbitmq.EmailDTO;
import org.niit.todotracker.todoservice.rabbitmq.MailProducer;
import org.niit.todotracker.todoservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TodoServiceImpl implements ITodoService{

   private final IUserRepository userRepository;
    private final UserProxy proxy;
    private final ArchiveProxy archiveProxy;

    private final MailProducer mailProducer;

    @Autowired
    public TodoServiceImpl(IUserRepository userRepository, UserProxy proxy, ArchiveProxy archiveProxy, MailProducer mailProducer) {
        this.userRepository = userRepository;
        this.proxy = proxy;
        this.archiveProxy = archiveProxy;
        this.mailProducer = mailProducer;
    }

    //Method Register To User On MongoDB
    @Override
    public User registerUser(User user) {
        if(userRepository.findById(user.getEmail()).isPresent()){
            throw new ResourceAlreadyExistException("user","Id", user.getEmail());
        }
        proxy.register(user);
        archiveProxy.register(user);
        return userRepository.save(user);
    }

    //Method To Get All User
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Method to Adding Users Category Into A Set

    @Override
    public User addUsersCategoryToSet(Category category, String userEmail) {
            String categoryId = UUID.randomUUID().toString();
            category.setCategoryId(categoryId);
        if (userRepository.findById(userEmail).isPresent()) {
            User user = userRepository.findById(userEmail).get();
            Set<Category> allCategories = user.getCategorySet();
            if (user.getCategorySet() != null) {
                boolean check = false;
                for (Category categories : allCategories) {
                    if (categories.getCategoryName().equals(category.getCategoryName())) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    allCategories.add(category);
                    user.setCategorySet(allCategories);
                }

            } else {
                user.setCategorySet(new HashSet<>());
                user.getCategorySet().add(category);
            }
            return userRepository.save(user);
        }
       throw new ResourceNotFoundException("user", "id", userEmail);
    }

    //Method To add User's Todos Into A Set
    @Override
    public User addUsersTodoToSet(Todo todo, String userEmail) {
        String todoId = UUID.randomUUID().toString();
        todo.setTodoId(todoId);
        if (userRepository.findById(userEmail).isPresent()) {
            User user = userRepository.findById(userEmail).get();
            Set<Todo> allTodos = user.getTodos();
            if (user.getTodos() != null) {
                boolean check = false;
                for (Todo todos : allTodos) {
                    if (todos.getTodoTitle().equals(todo.getTodoTitle())) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    allTodos.add(todo);
                    user.setTodos(allTodos);
                }

            } else {
                user.setTodos(new HashSet<>());
                user.getTodos().add(todo);
            }
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException("user", "id", userEmail);
    }

    //Method To Get All The Categories Of User As A Set
    @Override
    public Set<Category> getAllCategoriesOfUser(String userEmail) {
        if(userRepository.findById(userEmail).isEmpty()){
            throw new ResourceNotFoundException("user","id", userEmail);
        }
        return userRepository.findById(userEmail).get().getCategorySet();
    }

    //Method To Get All The Todos Of User As A Set
    @Override
    public Set<Todo> getAllTodosOfUser(String userEmail) {
        if(userRepository.findById(userEmail).isEmpty()){
            throw new ResourceNotFoundException("user","id",userEmail);
        }
        return userRepository.findById(userEmail).get().getTodos();
    }

    //Method To Add Todos Into Category
    @Override
    public Category addTodosIntoCategories(Todo todo, String categoryName, String userEmail) {
        String todoId = UUID.randomUUID().toString();
        todo.setTodoId(todoId);
        User user = userRepository.findById(userEmail).get();
        Set<Category> allCategories=userRepository.findById(userEmail).get().getCategorySet();
        Category category=null;
        for(Category cat:allCategories)
        {
            if(cat.getCategoryName().equals(categoryName))
            {
                category=cat;
            }
        }
        allCategories.remove(category);
        Set<Todo> todos=category.getTodos();
        if(todos!=null)
        {
            boolean check=false;
            for(Todo todoD:todos)
            {
                if(todoD.getTodoTitle().equals(todo.getTodoTitle()))
                {
                    check=true;
                }

            }
            if(!check) {
                todos.add(todo);
                category.setTodos(todos);
            }
        }
        else {
            category.setTodos(new HashSet<>());
            category.getTodos().add(todo);
        }
        allCategories.add(category);
        user.setCategorySet(allCategories);
        userRepository.save(user);
        return category;
    }

    //Method To Create Categories Of Any Todos
    @Override
    public Todo addCategoriesOfTodos(Category category, String todoTitle, String userEmail) {
        String categoryId = UUID.randomUUID().toString();
        category.setCategoryId(categoryId);
        User user=userRepository.findById(userEmail).get();
        Set<Todo> allTodos=userRepository.findById(userEmail).get().getTodos();
        Todo todo=null;
        for(Todo todos:allTodos)
        {
            if(todos.getTodoTitle().equals(todoTitle))
            {
                todo=todos;
            }
        }
        allTodos.remove(todo);
        Set<Category> categories=todo.getCategorySet();
        if(categories!=null)
        {
            boolean check=false;
            for(Category cat:categories)
            {
                if(cat.getCategoryName().equals(category.getCategoryName()))
                {
                    check=true;
                }

            }
            if(!check) {
                categories.add(category);
                todo.setCategorySet(categories);
            }
        }
        else {
            todo.setCategorySet(new HashSet<>());
            todo.getCategorySet().add(category);
        }
        allTodos.add(todo);
        user.setTodos(allTodos);
        userRepository.save(user);
        return todo;
    }

    //Method To Get User By E-Mail
    @Override
    public User searchUserByEmail(String emailId) {
        return userRepository.findById(emailId).orElseThrow(()->new ResourceNotFoundException("user","id", emailId));
    }


    //Method To Get Todos From Categories
    @Override
    public Set<Todo> getTodosFromCategory(String userEmail, String categoryName) {
        Set<Todo> todosOfCategory = null;
        boolean result = false;
        if (this.userRepository.findById(userEmail).isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userEmail);
        } else {
            User userData = userRepository.findById(userEmail).get();
            Set<Category> categories = userData.getCategorySet();
            if (categories != null) {
                Category categoryDetails = null;
                for (Category category : categories) {
                    if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
                        result = true;
                        categoryDetails = category;
                        break;
                    }
                }
                if (result) {
                    todosOfCategory = categoryDetails.getTodos();
                }
            }else{
                throw new ResourceNotFoundException();
            }
            return todosOfCategory;
        }
    }

    //fetching todos by todoId
    @Override
    public Todo getTodoByTodoId(String userEmail, String todoId, String categoryName) {
        if(userRepository.findById(userEmail).isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userEmail);
        }
        User user = userRepository.findById(userEmail).get();
        Set<Category> allCategory = user.getCategorySet();
        boolean categoryExist = false;
        Category category = null;
        for(Category categories:allCategory){
            if(categories.getCategoryName().equals(categoryName)){
                categoryExist = true;
                category = categories;
                break;
            }
        }
        if(categoryExist) {
            Set<Todo> allTodos = category.getTodos();
            boolean todoExist = false;
            Todo todo = null;
            for (Todo todos : allTodos) {
                if (todos.getTodoId().equals(todoId)) {
                    todoExist = true;
                    todo = todos;
                }
            }
            return todo;
        }
        throw new ResourceNotFoundException("user", "id", userEmail);
    }

    //updating users details
    @Override
    public User updateDetails(String userEmail, User user) {
        if(userRepository.findById(userEmail).isEmpty()){
            throw new ResourceNotFoundException("user", "id", userEmail);
        }
        User isExist = userRepository.findById(userEmail).get();
        isExist.setFullName(user.getFullName());
        isExist.setEmail(user.getEmail());
        isExist.setContactNo(user.getContactNo());
        isExist.setProfileImage(user.getProfileImage());
        userRepository.save(isExist);
        return isExist;
    }

    //Updating todos details
    @Override
    public Set<Todo> updateTodosDetails(String userEmail, Todo todo, String todoId) {
        if(userRepository.findById(userEmail).isEmpty()){
            throw new ResourceNotFoundException("user","id", userEmail);
        }
        User isExist = userRepository.findById(userEmail).get();
        Set<Todo> allTodos = isExist.getTodos();
        Todo singleTodo = null;
        for(Todo todos:allTodos){
            if(todos.getTodoId().equals(todoId)){
                singleTodo = todos;
                break;
            }
        }
        singleTodo.setTodoTitle(todo.getTodoTitle());
        singleTodo.setTodoDesc(todo.getTodoDesc());
        singleTodo.setDueDate(todo.getDueDate());
        singleTodo.setPriorities(todo.getPriorities());
        allTodos.add(singleTodo);
        userRepository.save(isExist);
        return allTodos;
    }

    //Deleting todos from list
    @Override
    public boolean deleteTodos(String email, String todoId) {
        if(userRepository.findById(email).isEmpty()){
            throw new ResourceNotFoundException("user", "id", email);
        }
        User user=userRepository.findById(email).get();
        Set<Todo> todoList=user.getTodos();
        Iterator<Todo> iterator=todoList.iterator();
        while(iterator.hasNext())
        {
            Todo todo=iterator.next();
            if(todo.getTodoId().equals(todoId))
            {
                iterator.remove();
            }
        }
        user.setTodos(todoList);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteCategory(String email, String categoryId) {
        User user=userRepository.findById(email).get();
        Set<Category> categorySet=user.getCategorySet();
        Iterator<Category> iterator=categorySet.iterator();
        while(iterator.hasNext())
        {
            Category category=iterator.next();
            if(category.getCategoryId().equals(categoryId))
            {
                iterator.remove();
            }
        }
        user.setCategorySet(categorySet);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteTodoFromCategory(String email, String todoId, String categoryName) {
        User user = userRepository.findById(email).get();
        Set<Category> categorySet = user.getCategorySet();
        Category cat = null;
        for (Category catDetails : categorySet) {
            if (catDetails.getCategoryName().equals(categoryName)) {
                cat = catDetails;
            }
        }
        Set<Todo> todosOfCategory = cat.getTodos();
        System.out.println(todosOfCategory);
        Iterator<Todo> iterator = todosOfCategory.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getTodoId().equals(todoId)) {
                iterator.remove();
            }
        }
        this.userRepository.save(user);
        return true;
    }

    @Override
    public int generateOtp() {
        int otp= ThreadLocalRandom.current().nextInt(1000,9999);
        return otp;
    }

    @Override
    public String verifyOtp(String email) {
        int otp = generateOtp();
        mailProducer.sendEmailDtoToQueue( new EmailDTO(email,"OTP From Todo App "+otp ,
                "OTP"));
        return otp+"";
    }

    @Override
    public User updatePassword(String email, User user)
    {
        if(userRepository.findById(email).isEmpty())
        {
            throw  new ResourceNotFoundException("user","id", email);
        }

        User isExist = userRepository.findById(email).get();
        UserDto dto = new UserDto(user.getEmail(), user.getPassword());
        proxy.updatePassword(dto,email);
        isExist.setEmail(user.getEmail());
        isExist.setPassword(user.getPassword());
        return userRepository.save(isExist);
    }

}
