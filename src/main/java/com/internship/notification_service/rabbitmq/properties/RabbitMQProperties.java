package com.internship.notification_service.rabbitmq.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {

    private String host;
    private String username;
    private String password;
    private Queues queues = new Queues();

    @Getter
    @Setter
    public static class Queues {

        private String fpExchangeName;
        private String fpQname;
        private String cpQname;
        private String aaQname;
        private String smQname;
        private String cancelReservationQname;
        private String deletePendingUserQueue;
        private String deletePendingBalanceQueue;
        private String verifyEmailQueue;
        private String disableJobQueue;
        private String enableJobQueue;
        private String cancelJobReservationsQueue;
        private String disableJobReviewsQueue;
        private String enableJobReviewsQueue;
        private String newReservationQueue;
        private String reservationEditedQueue;
        private String reservationAcceptedQueue;
        private String reservationRejectedQueue;
        private String userBanQueue;
        private String userUnbanQueue;
        private String userBanAppealAcceptQueue;
        private String userBanAppealRejectQueue;
        private String jobSuspendQueue;
        private String jobUnsuspendQueue;
    }
}
