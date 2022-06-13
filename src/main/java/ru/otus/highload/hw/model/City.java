package ru.otus.highload.hw.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * Cities directory entry
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@Getter
public class City {
    // unique identifier
    private Long id;

    // name of the city
    private String name;

    public City(Long id, String name) {
        Preconditions.checkArgument(id != null, "id is required");
        Preconditions.checkArgument(StringUtils.hasText(name), "name is required");
        this.id = id;
        this.name = name;
    }
}
