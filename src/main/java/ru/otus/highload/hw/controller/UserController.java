package ru.otus.highload.hw.controller;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.highload.hw.dao.FriendshipMapper;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.model.UserLogin;
import ru.otus.highload.hw.service.UserService;

import java.util.List;

/**
 * Контроллер представления пользовательской информации
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final FriendshipMapper friendshipMapper;
    private final UserService userService;

    /**
     * Страница с информацией об авторизованном пользователе
     * @param authentication
     * @param model
     * @return
     */
    @GetMapping({"/", "/user"})
    public String userPage(Authentication authentication, Model model){
        UserLogin userLogin = (UserLogin)authentication.getPrincipal();
        User user = userMapper.findById(userLogin.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("isUserProfile", true);
        initFriends(model, user);
        return "/user";
    }

    /**
     * Страница с информацией о пользователе по его идентификатору
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/user/{id}")
    public String userPageById(@PathVariable("id") long id, Model model){
        User user = userMapper.findById(id);
        model.addAttribute("user", user);
        initFriends(model, user);
        return "/user";
    }

    @PostMapping("/user/friends/{friendId}")
    public String addFriend(@PathVariable("friendId") long friendId, Authentication authentication, Model model) {
        UserLogin userLogin = (UserLogin)authentication.getPrincipal();
        User requestAuthor = userMapper.findById(userLogin.getUserId());

        User newFriend = userMapper.findById(friendId);

        userService.makeFriends(requestAuthor, newFriend);
        return "redirect:/user-list";
    }

    private void initFriends(Model model, User user) {
        List<User> friends = userMapper.findUserFriends(user);
        model.addAttribute("friends", friends);
    }


}
