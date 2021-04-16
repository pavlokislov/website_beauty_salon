package com.website.spring.service.controllers;

import com.website.spring.service.repositories.UserRepository;
import com.website.spring.service.transfer.MasterServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String StringgetHomePage(Model model) {
        List<Object[]> listMasters = (userRepository.getMastersAndTheirService());
        List<MasterServiceDto> masters = new ArrayList<MasterServiceDto>();
        for (Object[] objectsArray : listMasters) {
            String[] stringsArray = Arrays.stream(objectsArray)
                    .map(Object::toString)
                    .toArray(String[]::new);
            List<String> rawMasters = Arrays.asList(stringsArray);
            masters.add(MasterServiceDto.from(rawMasters));
        }
        model.addAttribute("masters", masters);
        log.info("Someone visited a site");
        return "home";
    }
}
