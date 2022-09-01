package ru.otus.highload.hw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

/**
 * Сущность Пользователь
 */
@Builder
@Getter
@EqualsAndHashCode(of = "id")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Идентификатор
     */
    @Setter
    private Long id;

    /**
     * Имя
     */
    private String firstName;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Пол
     */
    private Gender gender;

    /**
     * Интересы
     */
    private String interests;

    /**
     * Город
     */
    private City city;

    public String buildFio() {
        return String.format("%s %s", firstName, lastName);
    }
}
