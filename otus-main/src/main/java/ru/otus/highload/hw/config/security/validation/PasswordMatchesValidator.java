package ru.otus.highload.hw.config.security.validation;

import ru.otus.highload.hw.controller.ui.RegistrationController;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationController.UserDto user = (RegistrationController.UserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
