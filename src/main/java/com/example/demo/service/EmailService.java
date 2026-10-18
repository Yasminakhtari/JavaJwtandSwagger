package com.example.demo.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    // Just checking push and pull on git
    void sendEmail(String to, String subject, String userName, String msgContent) throws MessagingException;
}
