package ru.otus.highload.hw.controller.api.dto;

import lombok.Data;
import ru.otus.highload.hw.model.City;
import ru.otus.highload.hw.model.Gender;
import ru.otus.highload.hw.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewUserDto {
    // имя
    @NotBlank
    private String firstName;
    // фамилия
    @NotBlank
    private String lastName;
    // возраст
    @NotNull
    @Min(18)
    @Max(120)
    private Integer age;
    // пол
    @NotNull
    private Gender gender;
    // ид города
    @NotNull
    private Long cityId;
    //интересы
    @NotBlank
    private String interests;
    // логин
    @NotBlank
    private String login;
    // пароль
    @NotBlank
    private String password;
    //подтверждение пароля
    @NotBlank
    private String matchingPassword;

    // получить пользователя из ДТО
    public User initUser() {
        return User.builder()
                .age(age)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .city(new City(cityId, "empty"))
                .interests(interests)
                .build();
    }
}
