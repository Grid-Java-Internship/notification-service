package com.internship.notification_service.service;

import com.internship.notification_service.dto.NotificationCreateDto;
import com.internship.notification_service.model.Notification;

import java.util.List;

public interface NotificationService {

    void addNotification(NotificationCreateDto notificationCreateDto);

    List<Notification> getAllNotifications();

}
