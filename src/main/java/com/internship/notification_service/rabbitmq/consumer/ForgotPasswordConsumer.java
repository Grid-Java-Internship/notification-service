package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.rabbitmq.Message;
import com.internship.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
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


    /**
     * This method is annotated with {@link RabbitListener} to listen messages from rabbitmq queue named as
     * {@link Notification} entity and saves it to the database. Then it sends the notification to the user
     * via email using {@link EmailService}.
     *
     * @param message The message received from rabbitmq queue.
     */
    @RabbitListener(queues = "${configs.rabbitmq.queues.forgotPassword}")
    public void consumeMessage(Message message) {

        // TODO Make JWT utils

        Notification notification = notificationMapper.toEntity(message);
        notification.setNotificationId(UUID.randomUUID());
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        log.info("Forgot Password Received");

        String email = "arsenijepetrovic763@gmail.com";
        emailService.sendSimpleMail(email,
                message.getTitle(),
                message.getContent());
    }
}
