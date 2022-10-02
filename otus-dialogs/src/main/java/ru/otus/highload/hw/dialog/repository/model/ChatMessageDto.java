package ru.otus.highload.hw.dialog.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Setter
public class ChatMessageDto {

    private String id;

    private String chatId;

    private Long userId;

    private Instant createdAt;

    private String message;
}
