package com.alfika.backendecommerce;

import com.alfika.backendecommerce.configuration.notification.WebSockClientSessionHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class NotificationBoard {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        WebSockClientSessionHandler notifBoard = new WebSockClientSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(
                "ws://localhost:8080/broadcast", notifBoard
        );

        StompSession stompSession = sessionAsync.get();
        stompSession.subscribe("/topic/messages", notifBoard);

    }
}
