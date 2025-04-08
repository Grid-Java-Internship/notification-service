package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ForgotPasswordConsumer {

    private final NotificationProcessor notificationProcessor;

    /**
     * This method is annotated with {@link RabbitListener} to listen messages from rabbitmq queue named as
     * {@link Notification} entity and saves it to the database. Then it sends the notification to the user
     * via email using {@link EmailService}.
     *
     * @param message The message received from rabbitmq queue.
     */
    @RabbitListener(queues = "${spring.rabbitmq.queues.fp-qname}")
    public void consumeMessage(Message message) {

        // TODO Make JWT utils

        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about forgot password");
    }
}
