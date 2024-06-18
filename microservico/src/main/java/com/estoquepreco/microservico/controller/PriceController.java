package com.estoquepreco.microservico.controller;

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
import dto.PriceDTO;
import static constants.RabbitMQConstants.QUEUE_PRICE;

@RestController
@RequestMapping(value = "price")
public class PriceController {
    private static final Logger LOGGER =  LoggerFactory.getLogger(StockController.class);
    private final RabbitMQService rabbitMQService;

   @Autowired
   public PriceController(RabbitMQService rabbitMQService) {
       this.rabbitMQService = rabbitMQService;
   }

    @PutMapping
    private ResponseEntity<HttpStatus> alterPrice(@RequestBody PriceDTO priceDTO) {
        LOGGER.info("Received data {}", priceDTO);
        this.rabbitMQService.sendMessage(QUEUE_PRICE, priceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
