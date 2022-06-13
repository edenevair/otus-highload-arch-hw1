package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import ru.otus.highload.hw.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, first_name, last_name, age, gender, city_id FROM users WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property="city", column="city_id",
                    one=@One(select="ru.otus.highload.hw.dao.CityMapper.findById"))
    })
    User findById(Long id);

    @Select("SELECT id, first_name, last_name, age, gender, city_id  FROM users")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property="city", column="city_id",
                    one=@One(select="ru.otus.highload.hw.dao.CityMapper.findById"))
    })
    List<User> findAll();
}
