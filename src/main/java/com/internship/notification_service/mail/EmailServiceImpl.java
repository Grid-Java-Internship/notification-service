package com.internship.notification_service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_USERNAME}")
    private String username;

    @Value("${MAIL_PASS}")
    private String password;

    @Override
    public String sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setSubject(subject);
        message.setTo(to);
        message.setText(content);

        mailSender.send(message);

        log.info("SENT THE MESSAGE TO THE EMAIL: {}", to);

        return "SUCCESS";
    }

    @Override
    public String sendEmailWtihAttachment(String to, String subject, String content) {
        return "";
    }
}
