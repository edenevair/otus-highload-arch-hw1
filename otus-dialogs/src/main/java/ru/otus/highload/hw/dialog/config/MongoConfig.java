package ru.otus.highload.hw.dialog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.highload.hw.dialog.repository")
public class MongoConfig {
}
