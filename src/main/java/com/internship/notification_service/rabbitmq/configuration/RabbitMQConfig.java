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
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) { // IntelliJ bug. Bean is there
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
