package ru.otus.highload.hw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendshipRequestView {

    private User user;

    private FriendshipStatus status;

    private boolean isRequestedByUser;
}
