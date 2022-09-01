package ru.otus.highload.hw.config.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "mq")
public class RabbitMqProperties {
    private String login;
    private String password;
    private String host;
    private int port;
}
