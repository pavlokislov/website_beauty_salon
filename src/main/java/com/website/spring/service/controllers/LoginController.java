package com.website.spring.service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, ModelMap model , HttpServletRequest request) {
       if(authentication!=null){
           return "profile";
       }

        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error",true);
        }
        log.info("Login success");
        return "login";
    }
    
}
