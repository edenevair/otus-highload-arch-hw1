package ru.otus.highload.hw.news.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.NewsScrollerItem;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.news.event.NewsReceivedEvent;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqService {

    public static final String EXCHANGE_TOPIC_NAME = "NEWS_EXCHANGE_TOPIC";

    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;
    private final RabbitChannelHolder rabbitChannelHolder;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void init() {
        log.info("Initializing mq broker");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_TOPIC_NAME, BuiltinExchangeType.TOPIC);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create exchange", e);
        }
    }

    public void unsubscribe() {
        log.info("Unsubscribe from channel");
        rabbitChannelHolder.closeChannel();
    }

    public void postMessage(NewsScrollerItem item) {
        log.info("Posting new message to mq");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_TOPIC_NAME, BuiltinExchangeType.TOPIC);
            String json = objectMapper.writeValueAsString(item);
            channel.basicPublish(EXCHANGE_TOPIC_NAME, item.getFromUser().toString(), null, json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Unable to send post message", e);
        }
    }

    public void subscribeToFriendsNews(User user) {
        log.info("Subscribe for friends");
        List<Long> friends = userMapper.findUserFriends(user)
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());

        try {
            Channel channel = rabbitChannelHolder.getChannel(user);
            if (channel == null) {
                return;
            }
            String queueName = rabbitChannelHolder.getQueueName(user);

            for (Long friendId : friends) {
                channel.queueBind(queueName, EXCHANGE_TOPIC_NAME, friendId.toString());
            }
            channel.basicConsume(queueName, true, deliverCallback(user.getId()), consumerTag -> {});
        } catch (Exception e) {
            log.error("Unable to subscribe for wall posts");
            throw new RuntimeException("Unable to subscribe", e);
        }
    }

    public DeliverCallback deliverCallback(Long userId) {
        log.info("Mq callback for user with id " + userId);
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            NewsScrollerItem item = objectMapper.readValue(message, NewsScrollerItem.class);
            eventPublisher.publishEvent(NewsReceivedEvent.of(item, userId));
        };
    }
}
