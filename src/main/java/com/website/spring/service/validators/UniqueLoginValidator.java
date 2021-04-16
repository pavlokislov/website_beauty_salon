package com.website.spring.service.validators;

import com.website.spring.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UniqueLogin uniqueLogin) {
    }
    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return !repository.findOneByLogin(login).isPresent();
    }
}