package ru.otus.highload.hw.controller.api;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.highload.hw.controller.api.dto.FriendshipRequestDto;
import ru.otus.highload.hw.controller.api.dto.NewUserDto;
import ru.otus.highload.hw.controller.api.dto.ListDataHolder;
import ru.otus.highload.hw.dao.UserLoginMapper;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.Friendship;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.model.UserLogin;
import ru.otus.highload.hw.service.SecurityService;
import ru.otus.highload.hw.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер rest-api для работы с Пользователями
 */
@RestController
@RequiredArgsConstructor
public class UsersApiController {

    private final UserMapper userMapper;
    private final UserLoginMapper userLoginMapper;
    private final UserService userService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Получить всех пользователей
     * @return
     */
    @GetMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListDataHolder> findAllUsers() {
        List<User> allUsers = userMapper.findAll();
        ListDataHolder<User> dto = new ListDataHolder(allUsers);
        return ResponseEntity.ok(dto);
    }

    /**
     * Получить пользователя по id
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findById(@PathVariable("id") @NotNull Long id) {
        User user = userMapper.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Создать пользователя
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody NewUserDto userDTO) {
        Optional<UserLogin> userLogin = userLoginMapper.findByLogin(userDTO.getLogin());
        if (userLogin.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = userDTO.initUser();
        int insertedRows = userMapper.insert(user);
        Preconditions.checkState(insertedRows > 0, "User was not created");

        UserLogin login = UserLogin.builder()
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userId(user.getId())
                .build();

        insertedRows = userLoginMapper.insert(login);
        Preconditions.checkState(insertedRows > 0, "Login was not created");

        return ResponseEntity.ok(user);
    }

    /**
     * Получить список друзей пользователя
     * @param id
     * @return
     */
    @GetMapping(value = "/api/v1/users/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListDataHolder> findUserFriends(@PathVariable("id") @NotNull Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<User> friends = userMapper.findUserFriends(user);
        ListDataHolder<User> listDataHolder = new ListDataHolder(friends);
        return ResponseEntity.ok(listDataHolder);
    }

    /**
     * Добавить пользователя в друзья
     * @param id
     * @return
     */
    @PostMapping(value = "/api/v1/users/{id}/friends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FriendshipRequestDto> makeFriendshipRequest(@PathVariable("id") @NotNull Long id) {
        User requestingUser = securityService.getCurrentUserId();
        if (requestingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User requestedUser = userMapper.findById(id);
        if (requestedUser == null) {
            return ResponseEntity.notFound().build();
        }

        Friendship friendship = userService.makeFriends(requestingUser, requestedUser);
        FriendshipRequestDto requestDto = FriendshipRequestDto.of(friendship);
        return ResponseEntity.ok(requestDto);
    }
}
