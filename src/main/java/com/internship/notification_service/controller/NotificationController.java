package com.internship.notification_service.controller;

import com.internship.notification_service.dto.NotificationMessageDto;
import com.internship.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificationMessageDto>> getUserNotifications(@PathVariable("id") Long userId){

        return new ResponseEntity<>(notificationService.getAllNotifications(userId), HttpStatus.OK);
    }
}
