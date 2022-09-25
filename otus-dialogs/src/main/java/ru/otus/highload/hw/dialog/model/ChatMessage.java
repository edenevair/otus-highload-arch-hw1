package ru.otus.highload.hw.dialog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class ChatMessage {

    @Id
    private String id;

    private String chatId;

    private Long userId;

    private Instant createdAt;

    private String message;

}
