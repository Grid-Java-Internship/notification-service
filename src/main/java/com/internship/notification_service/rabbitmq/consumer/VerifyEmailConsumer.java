package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class VerifyEmailConsumer {

    private final NotificationProcessor notificationProcessor;

    @RabbitListener(queues = "${spring.rabbitmq.queues.verify-email-queue}")
    public void consumeMessage(Message message) {

        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about email verification");
    }
}
