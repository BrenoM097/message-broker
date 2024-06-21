package com.rabbimq.stock.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import dto.StockDTO;

@Component
public class StockConsumer {
    private final AES256TextEncryptor textEncryptor;
    private final ObjectMapper objectMapper;

    public StockConsumer(AES256TextEncryptor textEncryptor, ObjectMapper objectMapper) {
        this.textEncryptor = textEncryptor;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "ESTOQUE")
    public void consumer(String encryptedMessage) {
        try {
            // Decrypt the message
            String decryptedMessage = textEncryptor.decrypt(encryptedMessage);

            // Convert JSON to StockDTO object
            StockDTO stockDTO = objectMapper.readValue(decryptedMessage, StockDTO.class);

            System.out.println(stockDTO.quantity);
            System.out.println(stockDTO.SKU);
            System.out.println("_______________________________");

            Thread.sleep(12000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
