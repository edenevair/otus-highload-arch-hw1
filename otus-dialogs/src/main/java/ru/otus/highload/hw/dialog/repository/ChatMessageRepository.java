package ru.otus.highload.hw.dialog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.hw.dialog.model.Chat;
import ru.otus.highload.hw.dialog.model.ChatMessage;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    @Query(value="{chatId: ?0}", sort="{'createdAt': -1}")
    List<ChatMessage> findMessagesByChat(String chatId);
}
