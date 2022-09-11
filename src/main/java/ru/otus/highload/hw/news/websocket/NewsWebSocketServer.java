package ru.otus.highload.hw.news.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.otus.highload.hw.service.SecurityService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class NewsWebSocketServer {

    private Map<Long, String> sessionClientBinding = new ConcurrentHashMap<>();
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(Long userId, WebSocketSession session) {
        log.info("Registering session for " + userId);
        sessions.put(session.getId(), session);
        sessionClientBinding.put(userId, session.getId());
    }

    public void closeSession(WebSocketSession session) {
        log.info("Removing session");
        sessions.remove(session.getId());
    }

    public void sendMessage(Long userId, String message) {
        Optional<WebSocketSession> session = findUsersSession(userId);

        if (session.isEmpty()) {
            log.warn("No session for user " + 3);
            return;
        }

        try {
            session.get().sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Exception sending message for " + 3, e);
        }
    }

    private Optional<WebSocketSession> findUsersSession(Long userId) {
        String sessionId = sessionClientBinding.get(userId);
        if (sessionId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(sessions.get(sessionId));
    }
}
