package ru.otus.highload.hw.config.security.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.UserLogin;
import ru.otus.highload.hw.news.dao.NewsScrollerHolder;
import ru.otus.highload.hw.news.mq.MqService;
import ru.otus.highload.hw.model.User;

/**
 * Слушатель события успешной авторизации
 */
@Component
@RequiredArgsConstructor
public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final MqService mqService;
    private final UserMapper userMapper;
    private final NewsScrollerHolder newsScrollerHolder;

    /**
     * При успешной авторизации:
     * - прогружаем кэш ленты новостей из БД
     * - подписываемся на обновления друзей
     * @param event
     */
    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        User user = userMapper.findById(((UserLogin)event.getAuthentication().getPrincipal()).getUserId());
        mqService.subscribeToFriendsNews(user);
        newsScrollerHolder.findNewsForUser(user.getId());
    }

}