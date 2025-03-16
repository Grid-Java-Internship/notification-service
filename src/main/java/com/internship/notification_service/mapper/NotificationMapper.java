package com.internship.notification_service.mapper;

import com.internship.notification_service.dto.NotificationCreateDto;
import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.rabbitmq.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationMapper {
    @Mapping(source = "emailTo", target = "receiverEmail")
    NotificationMessageDto toDto(Notification notification);

    Notification toEntity(NotificationCreateDto notificationCreateDto);

    Notification toEntity(Message message);
}
