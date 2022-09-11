package com.alfika.backendecommerce.controller;

import com.alfika.backendecommerce.model.notification.Message;
import com.alfika.backendecommerce.model.notification.Sender;
import com.alfika.backendecommerce.service.websocket.ServerWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@PreAuthorize("hasRole('USER')")
public class ServerWebSocketController {

    @Autowired
    private ServerWebSocketService serverWebSocketService;

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public Message theNotification(Sender sender) throws InterruptedException {
        Thread.sleep(1000);
        return new Message(sender.getName(),": ");
    }

    @MessageMapping("/greetings")
    @SendToUser("/queue/greetings")
    public String reply(Principal user) {
        return  "consumer: " + user + " Login, Happy Shopping";
    }

}
