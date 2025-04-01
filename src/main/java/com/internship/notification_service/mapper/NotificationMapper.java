package com.internship.notification_service.mapper;

import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.model.FailedNotification;
import com.internship.notification_service.model.Notification;
import com.internship.notification_service.rabbitmq.communication.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationMapper {

    NotificationMessageDto toDto(Notification notification);

    Notification toEntity(Message message);

    FailedNotification toFailedNotification(Message message);

    Message toMessage(FailedNotification notification);

}
