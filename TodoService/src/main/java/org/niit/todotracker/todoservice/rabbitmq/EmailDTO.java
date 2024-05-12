package org.niit.todotracker.todoservice.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDTO {
    private String receiver,messageBody,subject;
}
