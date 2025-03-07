package com.internship.notification_service.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ForgotPasswordConsumer {

    @RabbitListener(queues = "forgotPasswordQueue")
    public void consumeMessage(String jwt) {
        // Make JWT utils
        Logger log = LoggerFactory.getLogger(ForgotPasswordConsumer.class);

        log.info("Forgot Password Received");

        System.out.println("FORGOT PASSWORD RECEIVED");
    }
}
