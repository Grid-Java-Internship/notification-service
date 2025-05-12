package com.internship.notification_service.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.internship.notification_service.rabbitmq.communication.Message;

public interface SseService {


    SseEmitter subscribe(String userId);


    void sendNotificationToUser(String userId, Message message);

}
