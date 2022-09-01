package ru.otus.highload.hw.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.highload.hw.model.NewsScrollerItem;
import ru.otus.highload.hw.news.dao.NewsScrollerHolder;
import ru.otus.highload.hw.news.dao.NewsScrollerMapper;
import ru.otus.highload.hw.news.mq.MqService;
import ru.otus.highload.hw.service.SecurityService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsScrollerService {

    private final MqService mqService;
    private final NewsScrollerMapper newsScrollerMapper;
    private final SecurityService securityService;
    private final NewsScrollerHolder newsHolder;

    public List<NewsScrollerItem> findLastWallItems(Long userId) {
        return newsHolder.findNewsForUser(userId);
    }

    public void addNews(NewsScrollerItem newItem) {
        log.info("New news item adding...");
        newItem.setFromUser(securityService.getCurrentUserId().getId());
        newsScrollerMapper.insert(newItem);
        mqService.postMessage(newItem);
        log.info("New news item added!");
    }

    public void newsReceived(NewsScrollerItem newItem, Long forUserId) {
        log.info("New news item received");
        newsHolder.addNewsItem(forUserId, newItem);
    }
}
