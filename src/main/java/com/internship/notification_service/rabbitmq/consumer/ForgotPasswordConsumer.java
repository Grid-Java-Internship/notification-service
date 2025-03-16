package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.rabbitmq.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ForgotPasswordConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "forgotPasswordQueue")
    public void consumeMessage(Message message) {
        // Make JWT utils
        log.info("Forgot Password Received");

        emailService.sendSimpleMail(message.getEmailTo(),
                message.getTitle(),
                message.getContent());
    }
}
