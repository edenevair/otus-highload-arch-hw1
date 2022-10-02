package ru.otus.highload.hw.dialog.model;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    private String id;

    private String chatId;

    private Long userId;

    private Instant createdAt;

    private String message;

    @Builder
    public ChatMessage(String chatId, Long userId, String message) {
        Preconditions.checkArgument(StringUtils.hasText(chatId), "Chat id is required");
        Preconditions.checkArgument(userId != null, "User id is required");
        Preconditions.checkArgument(StringUtils.hasText(message), "Message is required");

        this.chatId = chatId;
        this.userId = userId;
        this.message = message;
        this.createdAt = Instant.now();
    }
}
