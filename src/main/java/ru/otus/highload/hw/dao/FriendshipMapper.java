package ru.otus.highload.hw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
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
     * Заапрувить запрос
     * @param requestId
     * @return
     */
    @Update("update friendship set status = 'CONFIRMED' where id = #{requestId}")
    int acceptRequest(Long requestId);

    /**
     * Отклонить запрос
     * @param requestId
     * @return
     */
    @Update("update friendship set status = 'REJECTED' where id = #{requestId}")
    int rejectRequest(Long requestId);

    /**
     * Добавить дружескую связь
     * @param friendship
     * @return
     */
    @Insert("INSERT INTO friendship(user1_id, user2_id, direction, status) VALUES(#{user1.id}, #{user2.id}, #{direction}, #{status})")
    int insert(Friendship friendship);

    @Select("select id," +
            "    IF(direction = 'RIGHT', user2_id, user1_id) as user_id,\n" +
            "    status,\n" +
            "    IF(direction = 'RIGHT', user2_id, user1_id) = #{id} as isRequestedByUser\n" +
            "from friendship\n" +
            "where (user1_id = #{id} or user2_id = #{id}) and status != 'CONFIRMED'")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user",
                    one=@One(select="ru.otus.highload.hw.dao.UserMapper.findById")),
            @Result(column = "status", property = "status"),
            @Result(column = "isRequestedByUser", property = "isRequestedByUser"),
    })
    List<FriendshipRequestView> findOpenRequestForUser(User user);

    @Select("select id," +
            "    IF(direction = 'RIGHT', user2_id, user1_id) as user_id,\n" +
            "    status,\n" +
            "    IF(direction = 'RIGHT', user2_id, user1_id) = #{id} as isRequestedByUser\n" +
            "from friendship\n" +
            "where (user1_id = #{id} or user2_id = #{id})")
    @Results({
            @Result(column = "user_id",
                    one=@One(select="ru.otus.highload.hw.dao.UserMapper.findById"))
    })
    List<User> findSubscriptionForUsers(User user);
}
