package com.niit.MailService.rabbit;

import com.niit.MailService.domain.EmailData;
import com.niit.MailService.service.ConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private ConsumerService consumerService;
    @RabbitListener(queues = "mail_queue")
    public void getDtoFromQueue(EmailDTO emailDTO){

        EmailData emailData=new EmailData();
        emailData.setReceiver(emailDTO.getReceiver());
        emailData.setMessageBody(emailDTO.getMessageBody());
        emailData.setSubject(emailDTO.getSubject());

        consumerService.sendEmail(emailData);
    }


}
