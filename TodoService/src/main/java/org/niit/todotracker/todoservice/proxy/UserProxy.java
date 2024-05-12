package org.niit.todotracker.todoservice.proxy;

import org.niit.todotracker.todoservice.domain.User;
import org.niit.todotracker.todoservice.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-service", url="localhost:8090")
public interface UserProxy {

    @PostMapping("/userService/register")
    ResponseEntity<User> register(@RequestBody User user);


    @PutMapping("/userService/update/{emailId}")
    ResponseEntity<?> updatePassword(@RequestBody UserDto userDTO, @PathVariable String emailId);

}
