package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import ru.otus.highload.hw.model.City;
import ru.otus.highload.hw.model.UserLogin;

import java.util.Optional;

@Mapper
public interface UserLoginMapper {

    @Select("SELECT id, login, password FROM user_login WHERE login = #{login}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password")
    })
    Optional<UserLogin> findByLogin(String id);
}
