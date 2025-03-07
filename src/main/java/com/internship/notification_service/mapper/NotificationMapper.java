package com.internship.notification_service.mapper;

import com.internship.notification_service.dto.NotificationCreateDto;
import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.model.Notification;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    NotificationMessageDto toDto(Notification notification);

    Notification toEntity(NotificationCreateDto notificationCreateDto);

}
