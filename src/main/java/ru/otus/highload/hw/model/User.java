package ru.otus.highload.hw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private Long id;

    @NonNull
    private String firstName;

    private String lastName;

//    private Integer age;
//
//    private Gender gender;
//
//    private List<String> interests;
//
//    private String city;
}
