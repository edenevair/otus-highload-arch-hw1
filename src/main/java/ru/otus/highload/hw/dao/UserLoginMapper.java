package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
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
    Optional<UserLogin> findByLogin(String login);

    @SelectKey(statement = "SELECT LAST_INSERT_ID();", keyProperty = "id", before = false, resultType = Long.class)
    @Insert("INSERT INTO user_login(login, password, user_id) " +
            "VALUES(#{login}, #{password}, #{userId})")
    int insert(UserLogin login);
}
