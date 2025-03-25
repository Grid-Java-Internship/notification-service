package com.internship.notification_service.rabbitmq.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String FP_EXCHANGE_NAME = "notifications";

    private static final String FP_QNAME = "forgotPasswordQueue";

    private static final String CP_QNAME = "changePassword";

    private static final String AA_NAME = "activateAccount";

    private static final String CANCEL_RESERVATION_QNAME = "cancelReservationQueue";

    private static final String SM_NAME = "sendMail";

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
    Queue sendMessageQueue() {
        return new Queue(SM_NAME,false);
    }

    @Bean
    Queue cancelReservationQueue() {return new Queue(CANCEL_RESERVATION_QNAME,false);}

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
    public Binding bindingSendMessage(Queue sendMessageQueue, TopicExchange exchange)
    {
        return BindingBuilder.bind(sendMessageQueue)
                .to(exchange)
                .with("send.message");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
