package ru.otus.highload.hw.news.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.model.NewsScrollerItem;

import java.util.List;

/**
 * Холдер ленты новостей
 */
@Component
@RequiredArgsConstructor
public class NewsScrollerHolder {

    private final NewsScrollerMapper newsScrollerMapper;
    private final CacheManager cacheManager;

    /**
     * Получить новости для пользователя
     * @param userId
     * @return
     */
    @Cacheable("news")
    public List<NewsScrollerItem> findNewsForUser(Long userId) {
        return newsScrollerMapper.findNewsForUser(userId);
    }

    /**
     * Добавить новость для пользователя
     * @param userId
     * @param item
     */
    public void addNewsItem(Long userId, NewsScrollerItem item) {
        Cache cache = cacheManager.getCache("news");
        Cache.ValueWrapper wrapper = cache.get(userId);
        if (wrapper == null) {
            return;
        }
        List<NewsScrollerItem> news = (List<NewsScrollerItem>)wrapper.get();
        if (news == null) {
            return;
        }
        news.add(0, item);
        cache.put(userId, news);
    }

    /**
     * Очистить новости   пользователя
     * @param userId
     */
    @CacheEvict("news")
    public void clearHolderForUser(Long userId) {
    }
}
