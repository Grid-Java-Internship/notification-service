package com.internship.notification_service.service.impl;

import com.internship.notification_service.dto.NotificationCreateDto;
import com.internship.notification_service.dto.NotificationMessageDto;
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
     * Saves a new notification to the database based on the given
     * {@link NotificationCreateDto}.
     *
     * @param notificationCreateDto the notification to save
     */
    @Override
    public void addNotification(NotificationCreateDto notificationCreateDto) {
        Notification notification = notificationMapper.toEntity(notificationCreateDto);

        notificationRepository.save(notification);
    }

    /**
     * Finds all notifications for given user id and maps them to
     * {@link NotificationMessageDto} using {@link NotificationMapper}.
     *
     * @param userId the id of the user to find notifications for
     * @return a list of {@link NotificationMessageDto}
     */
    @Override
    public List<NotificationMessageDto> getAllNotifications(Long userId) {

        return notificationRepository.findAllByUserId(userId)
                .stream().map(notificationMapper::toDto).toList();
    }
}
