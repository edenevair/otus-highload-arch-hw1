package ru.otus.highload.hw.news.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.service.SecurityService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

@Component
@Log4j2
@RequiredArgsConstructor
public class RabbitChannelHolder {

    private final SecurityService securityService;
    private final ConnectionFactory connectionFactory;

    private Map<Long, Channel> channelMap = new ConcurrentHashMap<>();
    private Connection connection;

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        connection = connectionFactory.newConnection();
    }

    public Channel getChannel(User user) {

        if (user == null) {
            user = securityService.getCurrentUserId();
        }

        Channel channel = createChannelAndQueue(user);
        channelMap.putIfAbsent(user.getId(), channel);
        return channel;

    }

    public void closeChannel() {
        log.info("Close Rabbit channel for User={}", securityService.getCurrentUserId());
        Channel remove = channelMap.remove(securityService.getCurrentUserId().getId());
        if (remove != null) {
            try {
                remove.close();
            } catch (IOException | TimeoutException e) {
                log.error("Unable to close channel for user={}", securityService.getCurrentUserId(), e);
            }
        }
    }

    private synchronized Channel createChannelAndQueue(User user) {
        Channel channel = channelMap.get(user.getId());
        if (channel != null) {
            return channel;
        }
        try {
            channel = connection.createChannel();
        } catch (IOException e1) {
            log.warn("Need to refresh connection to create channel", e1);
            try {
                connection = connectionFactory.newConnection();
                return connection.createChannel();
            } catch (IOException | TimeoutException e2) {
                log.error("Unable to reset connection and create channel", e2);
                throw new RuntimeException(e2);
            }
        }
        createQueue(channel, user);
        return channel;
    }

    private void createQueue(Channel channel, User user) {
        try {
            channel.queueDeclare("user-"+user.getId(), true, true, true, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getQueueName(User user) {
        if (user != null) {
            return "user-"+user.getId();
        }
        return "user-"+securityService.getCurrentUserId().getId();

    }
}