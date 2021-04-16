package com.website.spring.service.controllers;

import com.website.spring.service.models.MasterSchedule;
import com.website.spring.service.models.Record;
import com.website.spring.service.models.ServiceStatus;
import com.website.spring.service.repositories.MasterScheduleRepository;
import com.website.spring.service.repositories.RecordRepository;
import com.website.spring.service.repositories.UserRepository;
import com.website.spring.service.security.details.UserDetailsImpl;
import com.website.spring.service.transfer.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.website.spring.service.transfer.UserDto.from;

@Slf4j
@Controller
public class MasterInfoController {

    @Autowired
    private MasterScheduleRepository masterScheduleRepository;
    @Autowired
    RecordRepository recordRepository;

    @GetMapping("/master")
    public String getMasterInfo(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = from(details.getUser());
        model.addAttribute("user", user);

        List<MasterSchedule> masterScheduleList = masterScheduleRepository.findAllByMasterId(user.getId());
        model.addAttribute("mastersSchedule", new ArrayList<>(masterScheduleList));
        log.info("Master page for " + user.getId());
        return "masterInfo";
    }

    @PostMapping("/master")
    public String sendMasterInfo(Model model, Long id) {
        if ((masterScheduleRepository.findServiceStatusById(id).compareTo(ServiceStatus.OPEN) == 0)) {
            String time = masterScheduleRepository.getTime(id);
            Long masterId = masterScheduleRepository.getMasterId(id);
            masterScheduleRepository.updateStatusByMasterId(String.valueOf(ServiceStatus.COMPLETED), time, masterId);
            recordRepository.updateRecordStatusMasterId(String.valueOf(ServiceStatus.COMPLETED), time, masterId);
        }
        return "redirect:/master";
    }
}
