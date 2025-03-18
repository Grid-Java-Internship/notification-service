package com.internship.notification_service.service;

import com.internship.notification_service.dto.NotificationCreateDto;
import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.model.Notification;

import java.util.List;

public interface NotificationService {

    List<NotificationMessageDto> getAllNotifications(Long userId);

}
