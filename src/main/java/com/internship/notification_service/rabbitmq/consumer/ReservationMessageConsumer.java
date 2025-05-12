package com.internship.notification_service.rabbitmq.consumer;

import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.rabbitmq.communication.NotificationProcessor;
import com.internship.notification_service.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ReservationMessageConsumer {

    private final NotificationProcessor notificationProcessor;
    @Value("${ADMIN-MAIL}")
    private final String adminMail;
    private final SseService sseService;


    /**
     * Listens to {@code cancelReservation} queue and processes the notifications.
     * The method is annotated with {@link RabbitListener} and listens for messages
     * in the queue named {@code cancelReservation}. When a message is received,
     * it is processed by {@link NotificationProcessor#processNotification(Message, String)}
     * and a notification is sent to the user.
     *
     * @param message the message received from the queue
     */
    @RabbitListener(queues = "${spring.rabbitmq.queues.cancel-reservation-qname}")
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
    @RabbitListener(queues = "${spring.rabbitmq.queues.new-reservation-queue}")
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
    @RabbitListener(queues = "${spring.rabbitmq.queues.reservation-edited-queue}")
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
    @RabbitListener(queues = "${spring.rabbitmq.queues.reservation-accepted-queue}")
    public void consumeAcceptReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about accepted reservation.");

        log.info("Received message for SSE push notification (Reservation Accepted) for user: {}", message.getUserId());


        sseService.sendNotificationToUser(String.valueOf(message.getUserId()), message);
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
    @RabbitListener(queues = "${spring.rabbitmq.queues.reservation-rejected-queue}")
    public void consumeRejectReservationMessage(Message message) {
        notificationProcessor.saveNotification(message);

        notificationProcessor.processNotification(message,
                "Processing notification about rejected reservation.");
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.notify-admin-queue}")
    public void notifyAdmin(Message message) {
        notificationProcessor.saveNotification(message);
        message.setEmailTo(adminMail);

        notificationProcessor.processNotification(message,
                "Processing notification about rejected reservation.");
    }
}
