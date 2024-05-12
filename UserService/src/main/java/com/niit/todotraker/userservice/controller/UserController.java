package com.niit.todotraker.userservice.controller;

import com.niit.todotraker.userservice.domain.User;
import com.niit.todotraker.userservice.security.ISecurity;
import com.niit.todotraker.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/userService")
public class UserController {

   private final IUserService iUserService;
    private final ISecurity iSecurity;


    @Autowired
    public UserController(IUserService iUserService, ISecurity iSecurity) {
        this.iUserService = iUserService;
        this.iSecurity = iSecurity;
    }

    //Request Handler Method to Register User
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
    return new ResponseEntity<>(iUserService.addUser(user), HttpStatus.CREATED);
    }

    //Request Handler Method to post data and generating JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        User result=iUserService.logIn(user.getEmail(),user.getPassword());
        if(result!=null)
        {
            Map<String,String> key=iSecurity.tokenGenerate(user);
            return new ResponseEntity<>(key,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Authentication failed",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<?>updatePassword(@RequestBody User user,@PathVariable String emailId)
    {
        return new ResponseEntity<>(iUserService.updatePassword(emailId,user),HttpStatus.OK);
    }


}
