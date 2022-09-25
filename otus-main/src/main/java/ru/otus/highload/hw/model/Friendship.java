package ru.otus.highload.hw.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Friendship {
    /**
     * Пользователь 1
     */
    private User user1;

    /**
     * Пользователь 2
     */
    private User user2;

    /**
     * Направление связи
     */
    private FriendshipDirection direction;

    /**
     * Статус
     */
    private FriendshipStatus status;

    private Friendship(User user1, User user2, FriendshipDirection friendshipDirection) {
        this.user1 = user1;
        this.user2 = user2;
        this.direction = friendshipDirection;
        this.status = FriendshipStatus.UNCONFIRMED;
    }

    /**
     * Статический конструктор
     * @param user1
     * @param user2
     * @return
     */
    public static Friendship of(User user1, User user2) {
        Preconditions.checkArgument(user1 != null);
        Preconditions.checkArgument(user2 != null);
        Preconditions.checkArgument(!user1.equals(user2), "Can not make a friend with yourself");

        if (user1.getId() < user2.getId()) {
            return new Friendship(user1, user2, FriendshipDirection.RIGHT);
        } else {
            return new Friendship(user2, user1, FriendshipDirection.LEFT);
        }
    }

    /**
     * Подтвердить дружбу
     */
    public void confirmFriendship() {
        this.status = FriendshipStatus.CONFIRMED;
    }

    /**
     * Отклонить дружбу
     */
    public void rejectFriendship() {
        this.status = FriendshipStatus.REJECTED;
    }

    /**
     * Проверить является ли польщователь инициатором дружбы
     * @param user
     * @return истина если пользователь иницатор дружбы
     */
    public boolean isUsersRequest(User user) {
        Preconditions.checkArgument(user != null);
        return (FriendshipDirection.RIGHT == direction && user1.equals(user))
                || (FriendshipDirection.LEFT == direction && user2.equals(user));
    }

    /**
     * Получить автора запроса
     * @return
     */
    public User getRequestAuthor() {
        return FriendshipDirection.RIGHT == direction ? user1 : user2;
    }

    /**
     * Получить запрашиваемого пользователя
     * @return
     */
    public User getRequestedUser() {
        return FriendshipDirection.RIGHT == direction ? user2 : user1;
    }

    // Направление связи
    private enum FriendshipDirection {
        // инициатор user1
        LEFT,
        // инициатор user2
        RIGHT
    }
}
