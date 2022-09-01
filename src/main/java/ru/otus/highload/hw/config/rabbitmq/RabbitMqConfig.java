package ru.otus.highload.hw.config.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMqConfig {

    private final RabbitMqProperties properties;

    @Bean
    public ConnectionFactory mqConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(properties.getHost());
        connectionFactory.setPort(properties.getPort());
        connectionFactory.setUsername(properties.getLogin());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }
}
