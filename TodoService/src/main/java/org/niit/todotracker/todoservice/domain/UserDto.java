package org.niit.todotracker.todoservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    @Id
    private String email;
    private String password;
}
