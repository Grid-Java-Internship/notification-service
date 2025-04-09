package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.constants.Constants;
import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class HoldTransactionConsumer {

    private final NotificationProcessor notificationProcessor;

    @RabbitListener(queues = Constants.HOLD_TRANSACTION_QUEUE)
    public void consumeMessage(Message message) {

        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about forgot password");
    }
}
