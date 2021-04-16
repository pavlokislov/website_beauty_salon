package com.website.spring.service.controllers;

import com.website.spring.service.transfer.BookingDto;
import com.website.spring.service.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import com.website.spring.service.security.details.UserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.website.spring.service.transfer.UserDto.from;
@Slf4j
@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String getProfilePage(ModelMap model, Authentication authentication) {
        if (authentication == null) {
            log.info("Access denied user is not authenticated");
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = from(details.getUser());
        model.addAttribute("user", user);
        model.addAttribute("book", new BookingDto());
        return "profile";
    }

    @PostMapping("/profile")
    public String postOrder(ModelMap modelMap, BookingDto bookValue) {
        return "redirect:/booking";
    }
}
