package com.niit.MailService.service;

import com.niit.MailService.domain.EmailData;

public interface ConsumerService {

    String sendEmail(EmailData emailData);


}
