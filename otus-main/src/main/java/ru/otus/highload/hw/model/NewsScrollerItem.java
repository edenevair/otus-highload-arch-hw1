package ru.otus.highload.hw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Запись на стене.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsScrollerItem {

    /**
     * идентификатор новости
     */
    private Long id;

    /**
     * автор
     */
    private Long fromUser;

    /**
     * ФИО автора
     */
    private String fio;

    /**
     * когда добавлена
     */
    private Instant createdAt;

    /**
     * содержимое
     */
    private String content;
}