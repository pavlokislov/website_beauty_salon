package com.website.spring.service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/booking/payment")
    public String getPaymentHTML(Model model){
        return "payment";
    }
}
