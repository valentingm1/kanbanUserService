package com.example.user_service.service.impl;

import com.example.user_service.config.RabbitMQConfig;
import com.example.user_service.events.UserValidationRequestEvent;
import com.example.user_service.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.example.user_service.events.UserValidationResponseEvent;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserValidationConsumer {

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserValidationConsumer(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_REQUEST)
    public void validateUsers(UserValidationRequestEvent event) {
        System.out.println("Validando usuarios para la tarea ID: " + event.getTaskId());

        Set<Long> validUserIds = event.getUserIds().stream()
                .filter(userRepository::existsById)
                .collect(Collectors.toSet());

        // ⚠ Asegurar que la respuesta siempre tenga un `taskId`
        if (event.getTaskId() == null) {
            System.out.println(" Error: Se recibió una solicitud con taskId nulo.");
            return;
        }

        UserValidationResponseEvent responseEvent = new UserValidationResponseEvent(event.getTaskId(), validUserIds);

        // Enviar respuesta a `task-service`
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_RESPONSE, responseEvent);
        System.out.println("Respuesta enviada con usuarios válidos: " + validUserIds);
    }
}