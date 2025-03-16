package com.internship.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
