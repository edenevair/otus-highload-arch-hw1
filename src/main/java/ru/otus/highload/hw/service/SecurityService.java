package ru.otus.highload.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.model.UserLogin;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserMapper userMapper;

    public User getCurrentUserId() {
        UserLogin userLogin = (UserLogin) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (userLogin == null) {
            throw new IllegalStateException("Can not get current user identifier");
        }
        return userMapper.findById(userLogin.getUserId());
    }
}
