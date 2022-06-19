package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, first_name, last_name, age, gender, city_id, interests FROM users WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "interests", column = "interests"),
            @Result(property="city", column="city_id",
                    one=@One(select="ru.otus.highload.hw.dao.CityMapper.findById"))
    })
    User findById(Long id);

    @Select("SELECT id, first_name, last_name, age, gender, city_id, interests FROM users")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "interests", column = "interests"),
            @Result(property="city", column="city_id",
                    one=@One(select="ru.otus.highload.hw.dao.CityMapper.findById"))
    })
    List<User> findAll();

    @SelectKey(statement = "SELECT LAST_INSERT_ID();", keyProperty = "id", before = false, resultType = Long.class)
    @Insert("INSERT INTO users(first_name, last_name, age, gender, city_id, interests) " +
            "VALUES(#{firstName}, #{lastName}, #{age}, #{gender}, #{city.id}, #{interests})")
    int insert(User user);


    @Select("select * from users where id in (\n" +
                "select distinct user2_id from friendship where user1_id = #{id}\n" +
                "union\n" +
                "select distinct user1_id from friendship where user2_id = #{id})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "interests", column = "interests"),
            @Result(property="city", column="city_id",
                    one=@One(select="ru.otus.highload.hw.dao.CityMapper.findById"))
    })
    List<User> findUserFriends(User user);
}
