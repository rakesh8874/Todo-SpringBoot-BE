package com.niit.ArchiveService.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ResourceAlreadyExistException extends RuntimeException{

    String resourceName;
    String fieldName;
    String fieldValue;

    public ResourceAlreadyExistException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s is Already Available with %s : %s ", resourceName,fieldName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
