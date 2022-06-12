package ru.otus.highload.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.User;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserListController {

    private final UserMapper userMapper;

    @GetMapping("/user-list")
    public String productPage(Model model){
        List<User> userList = userMapper.findAll();
        model.addAttribute("users", userList);
        return "/user-list";
    }

}
