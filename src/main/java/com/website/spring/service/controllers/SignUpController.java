package com.website.spring.service.controllers;

import com.website.spring.service.forms.UserForm;
import com.website.spring.service.services.SignUpService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping("signUp")
    public String getSignUpPage(UserForm userForm) {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        log.info("User form submitted ::" + userForm);
        service.signUp(userForm);
        return "redirect:/signUpSuccess";
    }

    @GetMapping("/signUpSuccess")
    public String signUpSuccess() {
        log.info("Sign up success");
        return "redirect:/login";
    }
}
