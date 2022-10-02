package ru.otus.highload.hw.dialog.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Document(collection = "chat")
@Sharded(shardKey = { "user1Id" })
@NoArgsConstructor
public class Chat {

    @Id
    private String id;

    private Long user1Id;

    private Long user2Id;

    private Instant updatedAt;

    @Builder
    public Chat(String id, Long user1Id, Long user2Id) {
        Preconditions.checkArgument(user1Id != null, "First user's id is required");
        Preconditions.checkArgument(user2Id != null, "Second user's id is required");
        Preconditions.checkArgument(!user1Id.equals(user2Id), "Unable to create chat for single person");

        this.id = id;
        this.user1Id = user1Id < user2Id ? user1Id : user2Id;
        this.user2Id = user1Id < user2Id ? user2Id : user1Id;
        this.updatedAt = Instant.now();
    }
}
