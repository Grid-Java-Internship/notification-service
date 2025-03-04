package com.internship.notification_service.repository;

import com.internship.notification_service.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, Long> {

    List<Notification> findAll();
}
