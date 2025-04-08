package com.internship.notification_service.rabbitmq.communication;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.FailedNotification;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.repository.FailedNotificationRepository;
import com.internship.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationProcessor {

    private final NotificationRepository notificationRepository;
    private final FailedNotificationRepository failedNotificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmailService emailService;

    public void saveNotification(Message message) {
        Notification notification = notificationMapper.toEntity(message);

        notification.setNotificationId(UUID.randomUUID());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Retryable(
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000),
            retryFor = RuntimeException.class
    )
    public void processNotification(Message message, String logMessage) {
        RetryContext retryContext = RetrySynchronizationManager.getContext();
        int retryCount = retryContext != null ? retryContext.getRetryCount() : 0;
        log.info("{} (Retry attempt: {})", logMessage, retryCount);

        emailService.sendSimpleMail(message.getEmailTo(),
                message.getTitle(),
                message.getContent());
    }

    @Recover
    public void handleFailedMessage(RuntimeException e, Message message) {
        FailedNotification failedNotification = notificationMapper.toFailedNotification(message);

        failedNotification.setNotificationId(UUID.randomUUID());
        failedNotification.setCreatedAt(LocalDateTime.now());
        failedNotification.setReason(e.getMessage());

        failedNotificationRepository.save(failedNotification);

        log.error("Failed to send notification. " +
                "It is stored in the database of failed notifications.");
    }


    @Scheduled(fixedRateString = "${scheduler.failed_notifications.time}")
    public void retryFailedNotifications() {
        List<FailedNotification> failedNotifications = failedNotificationRepository.findAll();
        for(FailedNotification failedNotification : failedNotifications) {
            log.info("Try to send failed notification");
            try {
                Message message = notificationMapper.toMessage(failedNotification);
                emailService.sendSimpleMail(
                        message.getEmailTo(),
                        message.getTitle(),
                        message.getContent());
                failedNotificationRepository.delete(failedNotification);
                log.info("Successfully sent failed notification");
            }catch (Exception e) {
                log.error("Scheduler failed to send failed notification. {}", e.getMessage());
            }
        }
    }

}
