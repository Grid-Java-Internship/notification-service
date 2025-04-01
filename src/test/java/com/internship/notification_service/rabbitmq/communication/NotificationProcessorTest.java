package com.internship.notification_service.rabbitmq.communication;

import com.internship.notification_service.mail.EmailService;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.FailedNotification;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.repository.FailedNotificationRepository;
import com.internship.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationProcessorTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private FailedNotificationRepository failedNotificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationProcessor notificationProcessor;

    private Message message;

    private Notification notification;

    private FailedNotification failedNotification;


    @BeforeEach
    void setUp() {
        message = Message
                .builder()
                .content("Your reservation has been canceled.")
                .title("Reservation Canceled")
                .emailTo("user@example.com")
                .userId(1L)
                .build();

        notification = Notification
                .builder()
                .content("Your reservation has been canceled.")
                .title("Reservation Canceled")
                .emailTo("user@example.com")
                .userId(1L)
                .build();

        failedNotification = FailedNotification
                .builder()
                .content("Your reservation has been canceled.")
                .title("Reservation Canceled")
                .emailTo("user@example.com")
                .userId(1L)
                .build();
    }

    @Test
    void saveNotification() {
        ArgumentCaptor<Notification> capturedNotification = ArgumentCaptor.forClass(Notification.class);

        when(notificationMapper.toEntity(message)).thenReturn(notification);

        notificationProcessor.saveNotification(message);

        verify(notificationRepository, times(1)).save(capturedNotification.capture());

        Notification savedNotification = capturedNotification.getValue();
        assertNotNull(savedNotification);
        assertEquals("Your reservation has been canceled.", savedNotification.getContent());
        assertEquals("Reservation Canceled", savedNotification.getTitle());
        assertEquals("user@example.com", savedNotification.getEmailTo());
        assertEquals(1L, savedNotification.getUserId());
        assertNotNull(savedNotification.getNotificationId());
        assertNotNull(savedNotification.getCreatedAt());

        verify(notificationMapper, times(1)).toEntity(message);
    }

    @Test
    void processNotificationWhenEmailServiceFails() {
        doThrow(new RuntimeException("Simulated failure"))
                .when(emailService)
                .sendSimpleMail(message.getEmailTo(), message.getTitle(), message.getContent());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> notificationProcessor.processNotification(message,
                        "Processing message..."));

        assertEquals("Simulated failure", exception.getMessage());
        verify(emailService, times(1)).sendSimpleMail(
                message.getEmailTo(),
                message.getTitle(),
                message.getContent());
    }

    @Test
    void processNotificationWhenEmailServiceSucceeds() {
        RetryContext mockContext = mock(RetryContext.class);
        when(mockContext.getRetryCount()).thenReturn(1);

        RetrySynchronizationManager.register(mockContext);

        when(emailService.sendSimpleMail(message.getEmailTo(),
                message.getTitle(),
                message.getContent())).thenReturn("SUCCESS");

        assertDoesNotThrow(() -> notificationProcessor.processNotification(message, "Processing message..."));

        verify(emailService, times(1)).sendSimpleMail(
                message.getEmailTo(),
                message.getTitle(),
                message.getContent());

        RetrySynchronizationManager.clear();
    }

    @Test
    void handleFailedMessage() {
        RuntimeException runtimeException = new RuntimeException("Simulated failure");
        ArgumentCaptor<FailedNotification> failedNotificationCaptor = ArgumentCaptor.forClass(FailedNotification.class);

        when(notificationMapper.toFailedNotification(message)).thenReturn(failedNotification);

        notificationProcessor.handleFailedMessage(runtimeException, message);

        verify(notificationMapper, times(1)).toFailedNotification(message);
        verify(failedNotificationRepository, times(1)).save(failedNotificationCaptor.capture());

        FailedNotification savedFailedNotification = failedNotificationCaptor.getValue();
        assertNotNull(savedFailedNotification);
        assertEquals("Your reservation has been canceled.", savedFailedNotification.getContent());
        assertEquals("Reservation Canceled", savedFailedNotification.getTitle());
        assertEquals("user@example.com", savedFailedNotification.getEmailTo());
        assertEquals(1L, savedFailedNotification.getUserId());
        assertNotNull(savedFailedNotification.getNotificationId());
        assertNotNull(savedFailedNotification.getCreatedAt());
        assertEquals(runtimeException.getMessage(), savedFailedNotification.getReason());
    }

    @Test
    void retryFailedNotificationsWhenListNotEmptyAndWhenEmailServiceSucceeds() {
        failedNotification.setReason("Simulated failure");
        failedNotification.setNotificationId(UUID.randomUUID());
        failedNotification.setCreatedAt(LocalDateTime.now());
        List<FailedNotification> failedNotifications = new ArrayList<>(List.of(failedNotification));

        when(failedNotificationRepository.findAll()).thenReturn(failedNotifications);
        when(notificationMapper.toMessage(failedNotification)).thenReturn(message);
        when(emailService.sendSimpleMail(message.getEmailTo(),
                message.getTitle(),
                message.getContent())).thenReturn("SUCCESS");

        assertDoesNotThrow(() -> notificationProcessor.retryFailedNotifications());

        verify(failedNotificationRepository, times(1)).findAll();
        verify(notificationMapper, times(1)).toMessage(failedNotification);
        verify(emailService, times(1)).sendSimpleMail(
                message.getEmailTo(),
                message.getTitle(),
                message.getContent());
        verify(failedNotificationRepository, times(1)).delete(failedNotification);
    }

    @Test
    void retryFailedNotificationsWhenListNotEmptyAndWhenEmailServiceFails() {
        failedNotification.setReason("Simulated failure");
        failedNotification.setNotificationId(UUID.randomUUID());
        failedNotification.setCreatedAt(LocalDateTime.now());
        List<FailedNotification> failedNotifications = new ArrayList<>(List.of(failedNotification));

        when(failedNotificationRepository.findAll()).thenReturn(failedNotifications);
        when(notificationMapper.toMessage(failedNotification)).thenReturn(message);
        doThrow(new RuntimeException("Simulated failure"))
                .when(emailService)
                .sendSimpleMail(message.getEmailTo(), message.getTitle(), message.getContent());

        notificationProcessor.retryFailedNotifications();

        verify(failedNotificationRepository, times(1)).findAll();
        verify(notificationMapper, times(1)).toMessage(failedNotification);
        verify(emailService, times(1)).sendSimpleMail(
                message.getEmailTo(),
                message.getTitle(),
                message.getContent());
        verify(failedNotificationRepository, never()).delete(failedNotification);
    }

    @Test
    void retryFailedNotificationsWhenListEmpty() {
        List<FailedNotification> failedNotifications = new ArrayList<>();

        when(failedNotificationRepository.findAll()).thenReturn(failedNotifications);

        notificationProcessor.retryFailedNotifications();

        verify(failedNotificationRepository, times(1)).findAll();
        verify(failedNotificationRepository, never()).delete(any());
        verify(emailService, never()).sendSimpleMail(any(), any(), any());
    }

}