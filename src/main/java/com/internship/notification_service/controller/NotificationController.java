package com.internship.notification_service.controller;

import com.internship.notification_service.model.Notification;
import com.internship.notification_service.service.NotificationService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("id") Long userId){

        return new ResponseEntity<>(notificationService.getAllNotifications(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Void> testingQueue(){

        return ResponseEntity.noContent().build();
    }


}
