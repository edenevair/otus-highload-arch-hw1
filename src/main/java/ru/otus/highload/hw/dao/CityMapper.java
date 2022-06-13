package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import ru.otus.highload.hw.model.City;

@Mapper
public interface CityMapper {

    @Select("SELECT id, name FROM cities WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    City findById(Long id);
}
