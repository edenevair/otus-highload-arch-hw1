package ru.otus.highload.hw.controller;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.highload.hw.dao.FriendshipMapper;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.FriendshipRequestView;
import ru.otus.highload.hw.model.FriendshipStatus;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.service.SecurityService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FriendshipRequestListController {

    private final FriendshipMapper friendshipMapper;
    private final SecurityService securityService;

    @GetMapping("/friendship-request-list")
    public String productPage(Model model){
        User currentUser = securityService.getCurrentUserId();

        List<FriendshipRequestView> requestViews = friendshipMapper.findOpenRequestForUser(currentUser);

        model.addAttribute("requests", requestViews);
        model.addAttribute("isFriendshipRequestList", true);
        return "/friendship-request-list";
    }


}
