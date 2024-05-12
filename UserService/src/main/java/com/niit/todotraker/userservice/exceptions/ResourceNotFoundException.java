package com.niit.todotraker.userservice.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
  private String resourceName;
  private  String field;
  private  String fieldValue;

    public ResourceNotFoundException(String resourceName, String field, String fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, field, fieldValue));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }
}
