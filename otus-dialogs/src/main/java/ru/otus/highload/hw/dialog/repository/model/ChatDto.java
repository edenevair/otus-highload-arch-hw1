package ru.otus.highload.hw.dialog.repository.model;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.Instant;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Setter
public class ChatDto {

    private String id;

    private Long user1Id;

    private Long user2Id;

    private Instant updatedAt;
}
