package com.internship.notification_service.service;

import com.internship.notification_service.dto.NotificationMessageDto;

import java.util.List;

public interface NotificationService {

    List<NotificationMessageDto> getAllNotifications(Long userId);

}
