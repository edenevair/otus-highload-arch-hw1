package ru.otus.highload.hw.controller;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.highload.hw.config.security.validation.PasswordMatches;
import ru.otus.highload.hw.dao.CityMapper;
import ru.otus.highload.hw.dao.UserLoginMapper;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.City;
import ru.otus.highload.hw.model.Gender;
import ru.otus.highload.hw.model.User;
import ru.otus.highload.hw.model.UserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserMapper userMapper;
    private final CityMapper cityMapper;
    private final UserLoginMapper userLoginMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/registration")
    public ModelAndView registerUser(@Valid @ModelAttribute("user") UserDto userDTO, BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("registration", "user", userDTO);
        }

        Optional<UserLogin> userLogin = userLoginMapper.findByLogin(userDTO.getLogin());
        if (userLogin.isPresent()) {
            ModelAndView mav = new ModelAndView("registration", "user", userDTO);
            mav.addObject("message", "Login already exists!");
            return mav;
        }

        User user = userDTO.initUser();
        int insertedRows = userMapper.insert(user);
        Preconditions.checkState(insertedRows > 0, "User was not created");

        UserLogin login = UserLogin.builder()
                .login(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.password))
                .userId(user.getId())
                .build();

        insertedRows = userLoginMapper.insert(login);
        Preconditions.checkState(insertedRows == 0, "Login was not created");

        return new ModelAndView("login");
    }

    @GetMapping("/registration")
    public String registrationPage(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        List<City> cities = cityMapper.findAll();
        model.addAttribute("cities", cities);

        return "registration";
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @PasswordMatches
    public static class UserDto {

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;
        @NotNull
        @Min(18)
        @Max(120)
        private Integer age;
        @NotNull
        private Gender gender;
        @NotNull
        private Long cityId;

        @NotBlank
        private String login;

        @NotBlank
        private String password;
        @NotNull
        @NotBlank
        private String matchingPassword;

        public User initUser() {
            return User.builder()
                    .age(age)
                    .firstName(firstName)
                    .lastName(lastName)
                    .gender(gender)
                    .city(new City(cityId, "empty"))
                    .build();
        }
    }
}
