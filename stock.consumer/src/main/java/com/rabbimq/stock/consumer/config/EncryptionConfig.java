package com.rabbimq.stock.consumer.config;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {
    @Value("${encryption.password}")
    private String encryptionPassword;

    @Bean
    public AES256TextEncryptor textEncryptor() {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(encryptionPassword);
        return textEncryptor;
    }

}
