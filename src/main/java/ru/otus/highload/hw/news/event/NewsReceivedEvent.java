package ru.otus.highload.hw.news.event;

import lombok.NonNull;
import lombok.Value;
import ru.otus.highload.hw.model.NewsScrollerItem;

@Value(staticConstructor = "of")
public class NewsReceivedEvent {
    @NonNull
    private NewsScrollerItem content;

    @NonNull
    private Long userId;
}
