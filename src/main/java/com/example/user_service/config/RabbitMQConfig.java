package com.example.user_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "user-validation-exchange";
    public static final String ROUTING_KEY_REQUEST = "request_routing_key";  // Recibe la solicitud
    public static final String ROUTING_KEY_RESPONSE = "response_routing_key"; // Envía la respuesta

    public static final String QUEUE_REQUEST = "request_queue";


    @Bean
    public Queue queueRequest(){
        return new Queue(QUEUE_REQUEST);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingRequest(Queue queueRequest,TopicExchange topicExchange){
        return BindingBuilder.bind(queueRequest).to(topicExchange).with(ROUTING_KEY_REQUEST);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}