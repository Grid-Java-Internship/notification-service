package com.internship.notification_service.rabbitmq.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String content;
    private String title;
    private String emailTo;
    private Long userId;
    private Long reservationId;
    private Long jobId;
}
