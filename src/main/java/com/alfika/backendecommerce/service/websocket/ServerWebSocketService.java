package com.alfika.backendecommerce.service.websocket;

import com.alfika.backendecommerce.configuration.notification.WebSockClientSessionHandler;
import com.alfika.backendecommerce.model.notification.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.security.Principal;
import java.util.concurrent.ExecutionException;

@Service
public class ServerWebSocketService {

    private static Logger logger = (Logger) LogManager.getLogger(ServerWebSocketService.class.toString());

   public void webSocketConnect (Principal user, String status, String product, int quantity) throws ExecutionException, InterruptedException {
       WebSocketClient client = new StandardWebSocketClient();

       WebSocketStompClient stompClient = new WebSocketStompClient(client);
       stompClient.setMessageConverter(new MappingJackson2MessageConverter());

       WebSockClientSessionHandler notifBoard = new WebSockClientSessionHandler();
       ListenableFuture<StompSession> sessionAsync = stompClient.connect(
               "ws://localhost:8080/broadcast", notifBoard
       );

       StompSession stompSession = sessionAsync.get();
       stompSession.subscribe("/topic/messages", notifBoard);

       logger.info(user.getName()+" "+status+" "+product+" x"+quantity);
       stompSession.send( "/topic/messages", new Sender(
               user.getName()+ " "+status+ " ->" + product +" ("+quantity+")"));
   }


}
