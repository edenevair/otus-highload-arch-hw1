package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.FriendshipRequestView;
import ru.otus.highload.hw.model.User;

import java.util.List;

/**
 * Маппер развязки дружбы между пользователями
 */
@Mapper
public interface FriendshipMapper {

    /**
     * Проверить существует ли дружеская связь
     * @param friendship
     * @return
     */
    @Select("SELECT EXISTS(SELECT 1 FROM friendship WHERE user1_id = #{user1.id} and user2_id = #{user2.id})")
    boolean friendshipExists(Friendship friendship);

    /**
     * Добавить дружескую связь
     * @param friendship
     * @return
     */
    @Insert("INSERT INTO friendship(user1_id, user2_id, direction, status) VALUES(#{user1.id}, #{user2.id}, #{direction}, #{status})")
    int insert(Friendship friendship);

    @Select("select IF(direction = 'RIGHT', user2_id, user1_id) as user_id,\n" +
            "    status,\n" +
            "    direction = 'RIGHT' as isRequestedByUser\n" +
            "from friendship\n" +
            "where (direction = 'RIGHT' and user1_id = #{id}) or (direction = 'LEFT' and user2_id = #{id})")
    @Results({
            @Result(column = "user_id", property = "user",
                    one=@One(select="ru.otus.highload.hw.dao.UserMapper.findById")),
            @Result(column = "status", property = "status"),
            @Result(column = "isRequestedByUser", property = "isRequestedByUser"),
    })
    List<FriendshipRequestView> findOpenRequestForUser(User user);
}
