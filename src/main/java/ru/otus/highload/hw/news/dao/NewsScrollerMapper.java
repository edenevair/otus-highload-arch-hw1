package ru.otus.highload.hw.news.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import ru.otus.highload.hw.model.NewsScrollerItem;

import java.util.List;

@Mapper
public interface NewsScrollerMapper {

    /**
     * Добавить новость
     * @param item
     * @return
     */
    @SelectKey(statement = "SELECT LAST_INSERT_ID();", keyProperty = "id", before = false, resultType = Long.class)
    @Insert("insert into news (content, user_from_id) VALUES(#{content}, #{fromUser})")
    int insert(NewsScrollerItem item);

    @Select("select news.*, concat(u.first_name, ' ', u.last_name) as fio from news\n" +
            "left join users u on u.id = news.user_from_id\n" +
            "where user_from_id in (select id from users where id in (\n" +
            "select distinct user2_id from friendship where user1_id = #{id} and status = 'CONFIRMED'\n" +
            "union\n" +
            "select distinct user1_id from friendship where user2_id = #{id} and status = 'CONFIRMED'))\n" +
            "order by created_at desc limit 1000")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fromUser", column = "user_from_id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "content", column = "content"),
            @Result(property = "fio", column = "fio")
    })
    List<NewsScrollerItem> findNewsForUser(Long userId);
}
