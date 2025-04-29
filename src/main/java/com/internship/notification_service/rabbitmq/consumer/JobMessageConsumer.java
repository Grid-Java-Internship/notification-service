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
public class JobMessageConsumer {
    private final NotificationProcessor notificationProcessor;

    @RabbitListener(queues = "${spring.rabbitmq.queues.job-suspend-queue}")
    public void consumeSuspendJobMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about job suspension");
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.job-unsuspend-queue}")
    public void consumeUnsuspendJobMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about job unsuspension");
    }
}
