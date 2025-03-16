package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.rabbitmq.Message;
import com.internship.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class ForgotPasswordConsumer {

    private final EmailService emailService;

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "forgotPasswordQueue")
    public void consumeMessage(Message message) {
        // TODO Make JWT utils

        Notification notification = notificationMapper.toEntity(message);
        notification.setNotificationId(UUID.randomUUID());
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        log.info("Forgot Password Received");

        emailService.sendSimpleMail(message.getEmailTo(),
                message.getTitle(),
                message.getContent());
    }
}
