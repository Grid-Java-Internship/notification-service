package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BanUserMessageConsumer {
    private final NotificationProcessor notificationProcessor;

    @RabbitListener(queues = "${spring.rabbitmq.queues.user-ban-queue}")
    public void consumeUserBanMessage(Message message) {
        notificationProcessor.saveNotification(message);
        notificationProcessor.processNotification(
                message,
                "Processing notification about user ban.");
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.user-unban-queue}")
    public void consumeUserUnbanMessage(Message message) {
        notificationProcessor.saveNotification(message);
        notificationProcessor.processNotification(
                message,
                "Processing notification about user unban.");
    }
}
