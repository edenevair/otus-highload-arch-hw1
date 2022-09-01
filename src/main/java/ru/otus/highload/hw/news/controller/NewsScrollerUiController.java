package ru.otus.highload.hw.news.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.NewsScrollerItem;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.news.NewsScrollerService;
import ru.otus.highload.hw.service.SecurityService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для представления с лентой новостей
 */
@Controller
@RequiredArgsConstructor
public class NewsScrollerUiController {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private final SecurityService securityService;
    private final NewsScrollerService scrollerService;

    /**
     *  Получить вью с лентой новостей
     * @param model
     * @return
     */
    @GetMapping("/news")
    public String productPage(Model model){

        User currentUser = securityService.getCurrentUserId();
        List<NewsScrollerItemDto> wallItemDtos = scrollerService.findLastWallItems(currentUser.getId())
                .stream()
                .map(wi -> NewsScrollerItemDto.of(wi.getContent(),
                        DATE_TIME_FORMATTER.format(wi.getCreatedAt().atZone(ZoneId.systemDefault())), wi.getFio()))
                .collect(Collectors.toList());

        model.addAttribute("items", wallItemDtos);
        model.addAttribute("newItem", NewsScrollerItemDto.of("", "",
                currentUser.getFirstName() + " " + currentUser.getLastName()));

        model.addAttribute("isWall", true);

        return "news";
    }

    /**
     * Добавить новость
     * @param itemDto
     * @return
     */
    @Transactional
    @PostMapping("/news")
    public String addNewPost(@Valid @ModelAttribute("newItem") NewsScrollerItemDto itemDto) {
        User currentUser = securityService.getCurrentUserId();
        NewsScrollerItem newItem = NewsScrollerItem.builder()
                .content(itemDto.getContent())
                .createdAt(Instant.now())
                .fio(currentUser.buildFio())
                .build();
        scrollerService.addNews(newItem);

        return "redirect:news";
    }
}

@Value(staticConstructor = "of")
class NewsScrollerItemDto {
    @NotBlank
    private String content;

    private String createdAt;

    private String fio;
}
