package ru.otus.highload.hw.config;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.highload.hw.controller.ui.RegistrationController;
import ru.otus.highload.hw.dao.FriendshipMapper;
import ru.otus.highload.hw.dao.UserLoginMapper;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.FriendshipRequestView;
import ru.otus.highload.hw.model.Gender;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.model.UserLogin;

import java.util.List;
import java.util.Optional;

@Component
@DependsOn("flywayInitializer")
@ConditionalOnProperty(prefix = "server", name = "mode", havingValue = "public", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class ServerInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserMapper userMapper;
    private final UserLoginMapper userLoginMapper;
    private final FriendshipMapper friendshipMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<UserLogin> login1 = userLoginMapper.findByLogin("ivanov");
        Optional<UserLogin> login2 = userLoginMapper.findByLogin("petrov");

        if (login1.isPresent() || login2.isPresent()) {
            return;
        }

        log.info("Initializing deafult users...");

        RegistrationController.UserDto userDto = new RegistrationController.UserDto();
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setAge(26);
        userDto.setCityId(1L);
        userDto.setGender(Gender.MALE);
        userDto.setLogin("ivanov");
        userDto.setPassword("1van0v09238fghw");
        userDto.setMatchingPassword("1van0v09238fghw");
        userDto.setInterests("none");

        User user1 = addUser(userDto);

        userDto = new RegistrationController.UserDto();
        userDto.setFirstName("Petr");
        userDto.setLastName("Petrov");
        userDto.setAge(32);
        userDto.setCityId(1L);
        userDto.setGender(Gender.MALE);
        userDto.setLogin("petrov");
        userDto.setPassword("p3tr0vsdjh49vdfw");
        userDto.setMatchingPassword("p3tr0vsdjh49vdfw");
        userDto.setInterests("none");

        User user2 = addUser(userDto);

        Friendship friendship = Friendship.of(user1, user2);
        friendshipMapper.insert(friendship);
        List<FriendshipRequestView> friendshipRequests = friendshipMapper.findOpenRequestForUser(user2);
        if (CollectionUtils.isEmpty(friendshipRequests)) {
            return;
        }
        for (FriendshipRequestView requestView : friendshipRequests) {
            int accepted = friendshipMapper.acceptRequest(requestView.getId());
            log.debug("friendship accepted " + (accepted == 1));
        }

    }

    private User addUser(RegistrationController.UserDto userDto) {
        User user = userDto.initUser();
        int insertedRows = userMapper.insert(user);
        Preconditions.checkState(insertedRows > 0, "User was not created");

        UserLogin login = UserLogin.builder()
                .login(userDto.getLogin())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userId(user.getId())
                .build();

        insertedRows = userLoginMapper.insert(login);
        Preconditions.checkState(insertedRows > 0, "Login was not created");
        return user;
    }


}
