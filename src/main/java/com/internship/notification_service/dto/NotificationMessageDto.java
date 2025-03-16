package com.internship.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDto {

    private String message;

    private String receiverEmail;

    private String title;

    private Long userId;
}
