package com.internship.notification_service.exception;

public class UnknownUserIdException extends RuntimeException {
    public UnknownUserIdException(String message) {
        super(message);
    }
}
