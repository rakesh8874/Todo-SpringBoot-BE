package org.niit.todotracker.todoservice.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    public void sendEmailDtoToQueue(EmailDTO emailDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"mail_routing",emailDTO);
    }

}
