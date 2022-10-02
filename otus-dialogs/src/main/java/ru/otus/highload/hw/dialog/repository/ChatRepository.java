package ru.otus.highload.hw.dialog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.hw.dialog.model.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    @Query(value="{$or : [{user1Id: ?0}, {user2Id: ?0}]}", sort="{'updatedAt': -1}")
    List<Chat> findChatsForUser(Long user);

}
