package ru.otus.highload.hw.controller.ui;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.service.SecurityService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserListController {

    private final UserMapper userMapper;
    private final SecurityService securityService;

    @GetMapping("/user-list")
    public String productPage(Model model){
        List<User> userList = userMapper.findAll();

        User currentUser = securityService.getCurrentUserId();
        List<User> friendsList = userMapper.findUserFriends(currentUser);

        List<UserListItem> userListItems = userList.stream()
                .map(u -> UserListItem.of(u, currentUser.getId().equals(u.getId()), friendsList.contains(u)))
                .collect(Collectors.toList());

        model.addAttribute("users", userListItems);
        model.addAttribute("isUserList", true);
        return "user-list";
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UserListItem {
        @NonNull
        private final User user;

        @Getter
        private final Boolean isCurrentUser;

        @Getter
        private final Boolean isFriend;


        public Long getId() {
            return user.getId();
        }
        public String getFirstName() {
            return user.getFirstName();
        }
        public String getLastName() {
            return user.getLastName();
        }
        public String getCityName() {
            return user.getCity() == null ? "-" : user.getCity().getName();
        }

        public static UserListItem of(User user, boolean isCurrentUser, boolean isFriend) {
            return new UserListItem(user, isCurrentUser, isFriend);
        }
    }

}
