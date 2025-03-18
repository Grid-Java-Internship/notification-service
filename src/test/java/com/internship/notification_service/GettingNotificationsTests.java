package com.internship.notification_service;

import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.exception.NoNotificationsOnResource;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.repository.NotificationRepository;
import com.internship.notification_service.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GettingNotificationsTests {
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationMapper notificationMapper;
    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification notification;

    private NotificationMessageDto notificationMessageDto;

    @BeforeEach
    void setUp(){
        notification = new Notification();
        notification.setUserId(1L);
        notification.setTitle("TEST TITLE");
        notification.setContent("TEST");
        notification.setEmailTo("test@test.com");
        notification.setCreatedAt(LocalDateTime.now());

        notificationMessageDto = new NotificationMessageDto(
            "TEST TITLE",
            "TEST",
            "test@test.com",
            notification.getCreatedAt()
        );
    }

    @Test
    void getAllNotificationsNoNotificationsFound(){
        when(notificationRepository.findAllByUserId(anyLong())).thenReturn(List.of());

        assertThrows(NoNotificationsOnResource.class, () -> notificationService.getAllNotifications(1L));
    }

    @Test
    void getAllNotificationsSuccess(){
        when(notificationRepository.findAllByUserId(1L)).thenReturn(List.of(notification));
        when(notificationMapper.toDto(any(Notification.class))).thenReturn(notificationMessageDto);
        assertDoesNotThrow(() -> notificationService.getAllNotifications(1L));

        verify(notificationRepository).findAllByUserId(anyLong());
        assertEquals(1, notificationService.getAllNotifications(1L).size());
    }

}
