package ru.otus.highload.hw.news.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.highload.hw.model.NewsScrollerItem;
import ru.otus.highload.hw.news.NewsScrollerService;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsReceivedEventListener {

    private final NewsScrollerService scrollerService;

    @EventListener
    public void onApplicationEvent(NewsReceivedEvent event) {
        log.info("New News item event received");
        NewsScrollerItem newItem = event.getContent();
        scrollerService.newsReceived(newItem, event.getUserId());
    }
}
