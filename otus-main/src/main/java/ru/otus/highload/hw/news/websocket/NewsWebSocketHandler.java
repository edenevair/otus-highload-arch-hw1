package ru.otus.highload.hw.news.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class NewsWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    NewsWebSocketServer socketServer;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long userId = Long.valueOf(message.getPayload());
        socketServer.registerSession(userId, session);
        socketServer.sendMessage(userId, "websocket registered");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Connection established!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketServer.closeSession(session);
    }

}
