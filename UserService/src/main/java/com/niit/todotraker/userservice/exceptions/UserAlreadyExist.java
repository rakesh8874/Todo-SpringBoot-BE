package com.niit.todotraker.userservice.exceptions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserAlreadyExist extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public UserAlreadyExist(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is Already Available with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
