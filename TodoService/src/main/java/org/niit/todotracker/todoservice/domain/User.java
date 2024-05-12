package org.niit.todotracker.todoservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "User_SignIn_Details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String fullName;
    private String profileImage;
    private String contactNo;

    @Id
    private String email;

    @Transient
    private String password;

    private Set<Category> categorySet;
    private Set<Todo> todos;

}
