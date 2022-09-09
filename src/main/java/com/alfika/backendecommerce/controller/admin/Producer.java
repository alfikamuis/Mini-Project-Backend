package com.alfika.backendecommerce.controller.admin;

import com.alfika.backendecommerce.model.notification.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange exchange;

    private static Logger logger = (Logger) LogManager.getLogger(Producer.class.toString());

    @PostMapping("/notif")
    public ResponseEntity<?> send(@RequestBody Message message, Principal principal){
        if (message.getMessages().isEmpty() || message.getMessages().equalsIgnoreCase("")){
            return new ResponseEntity<>("please, check your messages", HttpStatus.BAD_GATEWAY);
        }
        message.setSender(principal.getName());
        rabbitTemplate.convertAndSend(exchange.getName(),"routing.regular",message);

        logger.info(principal.getName()+" Sending messages - "+message.getMessages()+" to regular user");
        return new ResponseEntity<>("send "+message.getMessages()+" notification",HttpStatus.OK);
    }
}
