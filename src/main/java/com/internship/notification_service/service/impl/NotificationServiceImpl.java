package com.internship.notification_service.service.impl;

import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.exception.NoNotificationsOnResource;
import com.internship.notification_service.mapper.NotificationMapper;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.repository.NotificationRepository;
import com.internship.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    /**
     * Finds all notifications for given user id and maps them to
     * {@link NotificationMessageDto} using {@link NotificationMapper}.
     *
     * @param userId the id of the user to find notifications for
     * @return a list of {@link NotificationMessageDto}
     */
    @Override
    public List<NotificationMessageDto> getAllNotifications(Long userId) {

        List<Notification> notifications = notificationRepository.findAllByUserId(userId);

        if(notifications.isEmpty())
            throw new NoNotificationsOnResource("No notifications found for user with id: " + userId);

        return notifications
                .stream().map(notificationMapper::toDto).toList();
    }
}
