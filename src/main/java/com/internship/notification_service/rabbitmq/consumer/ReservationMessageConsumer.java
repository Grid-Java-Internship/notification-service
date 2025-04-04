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
public class ReservationMessageConsumer {

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
    public void consumeCancelReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about canceled reservation.");
    }

    /**
     * Listens to {@code newReservation} queue and processes the notifications.
     * This method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code newReservation}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = Constants.NEW_RESERVATION_QUEUE)
    public void consumeNewReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about new reservation.");
    }

    /**
     * Listens to {@code reservationEdited} queue and processes the notifications.
     * This method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code reservationEdited}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = Constants.RESERVATION_EDITED_QUEUE)
    public void consumeEditReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about edited reservation.");
    }

    /**
     * Listens to {@code reservationAccepted} queue and processes the notifications.
     * This method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code reservationAccepted}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = Constants.RESERVATION_ACCEPTED_QUEUE)
    public void consumeAcceptReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about accepted reservation.");
    }

    /**
     * Listens to {@code reservationRejected} queue and processes the notifications.
     * This method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code reservationRejected}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = Constants.RESERVATION_REJECTED_QUEUE)
    public void consumeRejectReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about rejected reservation.");
    }
}
