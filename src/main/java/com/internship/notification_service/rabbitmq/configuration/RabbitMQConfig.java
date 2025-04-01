package com.internship.notification_service.rabbitmq.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.internship.notification_service.constants.Constants.*;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    Queue forgotPasswordQueue() {
        return new Queue(FP_QNAME,false);
    }

    @Bean
    Queue changePasswordQueue() {
        return new Queue(CP_QNAME,false);
    }

    @Bean
    Queue activateAccountQueue() {
        return new Queue(AA_NAME,false);
    }

    @Bean
    Queue cancelReservationQueue() {
        return new Queue(CANCEL_RESERVATION_QNAME,false);
    }

    @Bean
    Queue deletePendingUserQueue() {
        return new Queue(DELETE_PENDING_USER_QUEUE, false);
    }

    @Bean
    Queue deletePendingBalanceQueue() {
        return new Queue(DELETE_PENDING_BALANCE_QUEUE, false);
    }

    @Bean
    Queue verifyEmailQueue() {
        return new Queue(VERIFY_EMAIL_QUEUE,false);
    }

    @Bean
    Queue disableJobQueue() {
        return new Queue(DISABLE_JOB_QUEUE, false);
    }

    @Bean
    Queue enableJobQueue() {
        return new Queue(ENABLE_JOB_QUEUE, false);
    }

    @Bean
    Queue cancelJobReservationsQueue() {
        return new Queue(CANCEL_JOB_RESERVATIONS_QUEUE, false);
    }

    @Bean
    Queue disableJobReviewsQueue() {
        return new Queue(DISABLE_JOB_REVIEWS_QUEUE, false);
    }

    @Bean
    Queue enableJobReviewsQueue() {
        return new Queue(ENABLE_JOB_REVIEWS_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(FP_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue forgotPasswordQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(forgotPasswordQueue)
                .to(exchange)
                .with("forgot.password");
    }

    @Bean
    public Binding bindingChangePassword(Queue changePasswordQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(changePasswordQueue)
                .to(exchange)
                .with("change.password");
    }

    @Bean
    public Binding bindingActivateAccount(Queue activateAccountQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(activateAccountQueue)
                .to(exchange)
                .with("activate.account");
    }

    @Bean
    public Binding bindingCancelReservation(Queue cancelReservationQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(cancelReservationQueue)
                .to(exchange)
                .with("cancel.reservation");
    }

    @Bean
    public Binding bindingDeletePendingUser(Queue deletePendingUserQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deletePendingUserQueue)
                .to(exchange)
                .with("delete.pending.user");
    }

    @Bean
    public Binding bindingDeletePendingBalance(Queue deletePendingBalanceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deletePendingBalanceQueue)
                .to(exchange)
                .with("delete.pending.balance");
    }

    @Bean
    public Binding bindingVerifyEmail(Queue verifyEmailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(verifyEmailQueue)
                .to(exchange)
                .with("verify.email");
    }

    @Bean
    public Binding bindingDisableJob(Queue disableJobQueue, TopicExchange exchange) {
        return BindingBuilder.bind(disableJobQueue)
                .to(exchange)
                .with("disable.job");
    }

    @Bean
    public Binding bindingEnableJob(Queue enableJobQueue, TopicExchange exchange) {
        return BindingBuilder.bind(enableJobQueue)
                .to(exchange)
                .with("enable.job");
    }

    @Bean
    public Binding bindingCancelJobReservations(Queue cancelJobReservationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(cancelJobReservationsQueue)
                .to(exchange)
                .with("cancel.job.reservations");
    }

    @Bean
    public Binding bindingDisableJobReviews(Queue disableJobReviewsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(disableJobReviewsQueue)
                .to(exchange)
                .with("disable.job.reviews");
    }

    @Bean
    public Binding bindingEnableJobReviews(Queue enableJobReviewsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(enableJobReviewsQueue)
                .to(exchange)
                .with("enable.job.reviews");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
