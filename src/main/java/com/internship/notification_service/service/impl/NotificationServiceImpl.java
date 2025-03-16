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

    @Override
    public void addNotification(NotificationCreateDto notificationCreateDto) {
        Notification notification = notificationMapper.toEntity(notificationCreateDto);

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationMessageDto> getAllNotifications(Long userId) {

        return notificationRepository.findAllByUserId(userId)
                .stream().map(notificationMapper::toDto).toList();
    }
}
