package ru.otus.highload.hw.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * Справочная сущность - Город
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@Getter
public class City {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Название города
     */
    private String name;

    public City(Long id, String name) {
        Preconditions.checkArgument(id != null, "id is required");
        Preconditions.checkArgument(StringUtils.hasText(name), "name is required");
        this.id = id;
        this.name = name;
    }
}
