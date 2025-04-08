package com.internship.notification_service.rabbitmq.configuration;

import com.internship.notification_service.rabbitmq.properties.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties properties;

    @Bean
    Queue forgotPasswordQueue() {
        return new Queue(properties.getQueues().getFpQname(), false);
    }

    @Bean
    Queue changePasswordQueue() {
        return new Queue(properties.getQueues().getCpQname(), false);
    }

    @Bean
    Queue activateAccountQueue() {
        return new Queue(properties.getQueues().getAaQname(), false);
    }

    @Bean
    Queue sendMessageQueue() {
        return new Queue(properties.getQueues().getSmQname(), false);
    }

    @Bean
    Queue cancelReservationQueue() {
        return new Queue(properties.getQueues().getCancelReservationQname(), false);
    }

    @Bean
    Queue deletePendingUserQueue() {
        return new Queue(properties.getQueues().getDeletePendingUserQueue(), false);
    }

    @Bean
    Queue deletePendingBalanceQueue() {
        return new Queue(properties.getQueues().getDeletePendingBalanceQueue(), false);
    }

    @Bean
    Queue verifyEmailQueue() {
        return new Queue(properties.getQueues().getVerifyEmailQueue(), false);
    }

    @Bean
    Queue disableJobQueue() {
        return new Queue(properties.getQueues().getDisableJobQueue(), false);
    }

    @Bean
    Queue enableJobQueue() {
        return new Queue(properties.getQueues().getEnableJobQueue(), false);
    }

    @Bean
    Queue cancelJobReservationsQueue() {
        return new Queue(properties.getQueues().getCancelJobReservationsQueue(), false);
    }

    @Bean
    Queue disableJobReviewsQueue() {
        return new Queue(properties.getQueues().getDisableJobReviewsQueue(), false);
    }

    @Bean
    Queue enableJobReviewsQueue() {
        return new Queue(properties.getQueues().getEnableJobReviewsQueue(), false);
    }

    @Bean
    Queue newReservationQueue() {
        return new Queue(properties.getQueues().getNewReservationQueue(), false);
    }

    @Bean
    Queue reservationEditedQueue() {
        return new Queue(properties.getQueues().getReservationEditedQueue(), false);
    }

    @Bean
    Queue reservationAcceptedQueue() {
        return new Queue(properties.getQueues().getReservationAcceptedQueue(), false);
    }

    @Bean
    Queue reservationRejectedQueue() {
        return new Queue(properties.getQueues().getReservationRejectedQueue(), false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(properties.getQueues().getFpExchangeName());
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
    public Binding bindingSendMessage(Queue sendMessageQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(sendMessageQueue)
                .to(exchange)
                .with("send.message");
    }

    @Bean
    public Binding bindingNewReservation(Queue newReservationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newReservationQueue)
                .to(exchange)
                .with("new.reservation");
    }

    @Bean
    public Binding bindingReservationEdited(Queue reservationEditedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reservationEditedQueue)
                .to(exchange)
                .with("reservation.edited");
    }

    @Bean
    public Binding bindingReservationAccepted(Queue reservationAcceptedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reservationAcceptedQueue)
                .to(exchange)
                .with("reservation.accepted");
    }

    @Bean
    public Binding bindingReservationRejected(Queue reservationRejectedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reservationRejectedQueue)
                .to(exchange)
                .with("reservation.rejected");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(properties.getHost());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
