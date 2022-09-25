package ru.otus.highload.hw.config.security.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.model.UserLogin;
import ru.otus.highload.hw.news.dao.NewsScrollerHolder;
import ru.otus.highload.hw.news.mq.MqService;

/**
 * Слушатель события завершения http-сессии
 */
@Component
@RequiredArgsConstructor
public class SessionExpiredEventListener implements ApplicationListener<SessionDestroyedEvent> {

    private final MqService mqService;
    private final NewsScrollerHolder newsScrollerHolder;

    /**
     * При завершении сесии освобождаем занимаемые ресурсы
     * @param event
     */
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        // убираем себя из слушателей очереди новостей
        mqService.unsubscribe();

        // освобождаем кэш с лентой новостей
        UserLogin login = (UserLogin) event.getSecurityContexts().stream()
                .map(sc -> sc.getAuthentication())
                .filter(sc -> sc.getPrincipal() != null)
                .map(sc -> sc.getPrincipal())
                .findFirst().orElseThrow(() -> new IllegalStateException());
        newsScrollerHolder.clearHolderForUser(login.getUserId());
    }
}
