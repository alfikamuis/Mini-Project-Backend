package com.alfika.backendecommerce.controller.user;

import com.alfika.backendecommerce.controller.admin.Producer;
import com.alfika.backendecommerce.model.notification.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@Component
@Slf4j
public class Consumer {

    private static Logger logger = (Logger) LogManager.getLogger(Consumer.class.toString());

    /*@GetMapping("/check-promo")
    @RabbitListener(queues = "regular.user")
    private ResponseEntity<?> receiveNotification(Message message){

        logger.info("promo notification received from admin"+ message);
        return new ResponseEntity<>("info: "+message.getMessages(), HttpStatus.OK);
    }
     */
    @GetMapping("/check-promo")
    @RabbitListener(queues = "regular.user")
    private void receiveNotification(Message message){

        logger.info("promo notification received from admin"+ message);
    }
}
