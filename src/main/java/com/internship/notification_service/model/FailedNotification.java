package com.internship.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "failed_notifications")
public class FailedNotification {

    @Id
    private UUID notificationId;

    private String content;

    private String title;

    private Long userId;

    private String emailTo;

    private LocalDateTime createdAt;

    private String reason;
}
