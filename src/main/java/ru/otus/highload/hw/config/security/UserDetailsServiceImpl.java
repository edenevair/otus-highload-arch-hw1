package ru.otus.highload.hw.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.highload.hw.dao.UserLoginMapper;
import ru.otus.highload.hw.model.UserLogin;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserLoginMapper userLoginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserLogin> userLogin = userLoginMapper.findByLogin(username);
        if (userLogin.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return userLogin.get();
    }
}
