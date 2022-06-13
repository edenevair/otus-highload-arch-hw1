package ru.otus.highload.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.User;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @GetMapping("/user/{id}")
    public String productPage(@PathVariable("id") long id, Model model){
        User user = userMapper.findById(id);
        model.addAttribute("user", user);
        return "/user";
    }

}
