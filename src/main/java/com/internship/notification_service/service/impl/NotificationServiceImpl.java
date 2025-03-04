package com.internship.notification_service.service.impl;

import com.internship.dto.NotificationCreateDto;
import com.internship.dto.NotificationDto;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.repository.NotificationRepository;
import com.internship.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    @Override
    public void addNotification(NotificationCreateDto notificationCreateDto) {
        Notification notification = new Notification();

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
