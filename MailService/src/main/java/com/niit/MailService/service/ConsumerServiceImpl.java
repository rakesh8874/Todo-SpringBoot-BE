package com.niit.MailService.service;

import com.niit.MailService.domain.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService{
@Autowired
    private JavaMailSender javaMailSender;

@Value("${spring.mail.username}")
private String sender;
    @Override
    public String sendEmail(EmailData emailData) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailData.getReceiver());
            simpleMailMessage.setText(emailData.getMessageBody());
            simpleMailMessage.setSubject(emailData.getSubject());

            javaMailSender.send(simpleMailMessage);
            return "Successful";
        } catch (Exception e){
            e.printStackTrace();
            return "Failure";
        }
    }
}
