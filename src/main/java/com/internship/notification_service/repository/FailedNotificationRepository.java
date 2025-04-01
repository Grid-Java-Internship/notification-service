package com.internship.notification_service.repository;

import com.internship.notification_service.model.FailedNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedNotificationRepository extends MongoRepository<FailedNotification, Long> {

}
