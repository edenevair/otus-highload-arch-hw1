package ru.otus.highload.hw.controller.api.dto;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.FriendshipStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FriendshipRequestDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long requestedUserId;
    private Long requestingUserId;
    private FriendshipStatus status;

    public static FriendshipRequestDto of(Friendship friendship) {
        Preconditions.checkArgument(friendship != null, "Friendship data is required");
        return new FriendshipRequestDto(friendship.getRequestAuthor().getId(), friendship.getRequestedUser().getId(),
                friendship.getStatus());
    }
}
