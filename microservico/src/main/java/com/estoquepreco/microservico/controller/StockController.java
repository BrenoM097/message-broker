package com.estoquepreco.microservico.controller;

import dto.StockDTO;
import static constants.RabbitMQConstants.QUEUE_STOCK;
import com.estoquepreco.microservico.service.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "stock")
public class StockController {
    private static final Logger LOGGER =  LoggerFactory.getLogger(StockController.class);
    private final RabbitMQService rabbitMQService;

    @Autowired
    public StockController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity<HttpStatus> alterStock(@RequestBody StockDTO stockDTO) {
        LOGGER.info("Received data: {}", stockDTO);
        rabbitMQService.sendMessage(QUEUE_STOCK, stockDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
