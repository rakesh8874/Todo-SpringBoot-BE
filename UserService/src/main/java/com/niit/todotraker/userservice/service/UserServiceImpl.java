package com.niit.todotraker.userservice.service;

import com.niit.todotraker.userservice.domain.User;
import com.niit.todotraker.userservice.exceptions.ResourceNotFoundException;
import com.niit.todotraker.userservice.exceptions.UserAlreadyExist;
import com.niit.todotraker.userservice.rabbitmqconfig.EmailDTO;
import com.niit.todotraker.userservice.rabbitmqconfig.MailProducer;
import com.niit.todotraker.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
   private final UserRepository userRepository;
   private final MailProducer mailProducer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailProducer mailProducer) {
        this.userRepository = userRepository;
        this.mailProducer = mailProducer;
    }
    //Method to Add User In MySQL
    @Override
    public User addUser(User user) {
    if(userRepository.findById(user.getEmail()).isEmpty()){
        User userData = userRepository.save(user);
        EmailDTO emailDTO=new EmailDTO(userData.getEmail(),"Welcome To Our Application","Your Registration Successful");
        mailProducer.sendEmailDtoToQueue(emailDTO);
        return userData;
    }
        throw new UserAlreadyExist("user","Id",user.getEmail());
    }

    //Method To Login User
    @Override
    public User logIn(String email,String password) {
       if(userRepository.findById(email).isEmpty()){
           throw new ResourceNotFoundException("user","Id", email);
       }else {
           User user=userRepository.findById(email).get();
           if(user.getEmail().equals(email)&& user.getPassword().equals(password)){
               return user;
           }
       }
        throw new ResourceNotFoundException("user", "Id", email);
    }

    @Override
    public User updatePassword(String email, User user)
    {
        if(userRepository.findById(email).isEmpty())
        {
            return null;
        }

        User isExist = userRepository.findById(email).get();
        isExist.setEmail(user.getEmail());
        isExist.setPassword(user.getPassword());
        return userRepository.save(isExist);
    }


}
