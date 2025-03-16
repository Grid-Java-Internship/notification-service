package com.internship.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationMessageDto {

    private String content;

    private String receiverEmail;

    private String title;

    private LocalDateTime createdAt;
}
