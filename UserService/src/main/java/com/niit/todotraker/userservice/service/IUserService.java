package com.niit.todotraker.userservice.service;

import com.niit.todotraker.userservice.domain.User;

public interface IUserService {


    //Method to add User Into MySql
    User addUser(User user);
    //Method to Login User
    User logIn(String email,String password);

    public User updatePassword(String email,User user);

}
