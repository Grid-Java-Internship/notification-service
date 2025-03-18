package com.internship.notification_service.exception;

public class NoNotificationsOnResource extends RuntimeException {
    public NoNotificationsOnResource(String message) {
        super(message);
    }
}
