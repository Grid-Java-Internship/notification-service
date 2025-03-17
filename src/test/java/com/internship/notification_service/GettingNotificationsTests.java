package com.internship.notification_service;

import com.internship.notification_service.exception.NoNotificationsOnResource;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.repository.NotificationRepository;
import com.internship.notification_service.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GettingNotificationsTests {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final NotificationServiceImpl notificationService;

    public GettingNotificationsTests(){
        this.notificationRepository = Mockito.mock(NotificationRepository.class);
        this.notificationMapper = Mockito.mock(NotificationMapper.class);
        this.notificationService = new NotificationServiceImpl(notificationRepository, notificationMapper);
    }

    @Test
    void getAllNotificationsNoNotificationsFound(){
        when(notificationRepository.findAllByUserId(anyLong())).thenReturn(List.of());

        assertThrows(NoNotificationsOnResource.class, () -> notificationService.getAllNotifications(1L));
    }

}
