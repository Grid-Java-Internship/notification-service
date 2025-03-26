package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.constants.Constants;
import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CancelReservationConsumer {

    private final NotificationProcessor notificationProcessor;

    /**
     * Listens to {@code cancelReservation} queue and processes the notifications.
     * The method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code cancelReservation}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = Constants.CANCEL_RESERVATION_QNAME)
    public void consumeMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about canceled reservation.");
    }
}
