package ru.otus.highload.hw.dialog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class Chat {

    @Id
    private String id;

    private Long user1Id;

    private Long user2Id;

    private Instant updatedAt;
}
