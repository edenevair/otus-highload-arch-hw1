package ru.otus.highload.hw.service;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.hw.dao.FriendshipMapper;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.User;

/**
 * Сервис для рабты с доменным предсавтелнием пользователя
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final FriendshipMapper friendshipMapper;

    /**
     * Сделать пользователей друзьями
     * @param from
     * @param to
     * @return
     */
    public Friendship makeFriends(User from, User to) {
        Preconditions.checkArgument(from != null, "User requesting for friendship is required");
        Preconditions.checkArgument(to != null, "User requested for friendship is required");

        Friendship friendship = Friendship.of(from, to);

        // проверяем что такой связи нет, если есть то не создаем дубликат
        if (friendshipMapper.friendshipExists(friendship)) {
            return friendship;
        }

        int insertedColumns = friendshipMapper.insert(friendship);
        Preconditions.checkArgument(insertedColumns > 0, "Could not insert friendship record");

        return friendship;
    }
}
