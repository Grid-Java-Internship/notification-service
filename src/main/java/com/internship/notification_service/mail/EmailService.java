package com.internship.notification_service.mail;

public interface EmailService {

    String sendSimpleMail(String to, String subject, String content);

    String sendEmailWtihAttachment(String to, String subject, String content);
}
