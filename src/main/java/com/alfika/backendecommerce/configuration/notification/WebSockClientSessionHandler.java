package com.alfika.backendecommerce.configuration.notification;

import com.alfika.backendecommerce.model.notification.Sender;
import com.alfika.backendecommerce.service.websocket.ServerWebSocketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class WebSockClientSessionHandler extends StompSessionHandlerAdapter {

    private static Logger logger = (Logger) LogManager.getLogger(ServerWebSocketService.class.toString());

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Sender.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        //System.out.println("info - "+((Sender)payload).getName() );
        logger.info("info - "+((Sender)payload).getName() );
    }
}
