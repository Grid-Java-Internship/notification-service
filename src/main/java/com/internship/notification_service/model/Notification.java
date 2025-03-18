package com.internship.notification_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private UUID notificationId;

    private String content;

    private String title;

    private Long userId;

    private String emailTo;

    private LocalDateTime createdAt;

}
