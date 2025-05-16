package com.internship.notification_service.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_USERNAME}")
    private final String username;

    @Override
    public String sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content,false);

            mailSender.send(mimeMessage);
            log.info("SENT THE MESSAGE TO THE REAL EMAIL: {}", to);

        }catch (Exception e){
            log.error("Error in sendSimpleMail method. {}", e.getMessage());
        }


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
