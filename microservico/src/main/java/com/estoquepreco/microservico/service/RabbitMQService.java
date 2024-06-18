package com.estoquepreco.microservico.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    private final RabbitTemplate rabbitTemplate;
    private final AES256TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate, AES256TextEncryptor textEncryptor, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.textEncryptor = textEncryptor;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String queueName, Object message) {
        try {
            // Convert object to JSON
            String jsonMessage = objectMapper.writeValueAsString(message);

            // Encrypt the JSON message
            String encryptedMessage = textEncryptor.encrypt(jsonMessage);

            this.rabbitTemplate.convertAndSend(queueName, encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
