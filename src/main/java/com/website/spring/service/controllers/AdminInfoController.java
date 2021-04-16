package com.website.spring.service.controllers;

import com.website.spring.service.models.MasterSchedule;
import com.website.spring.service.models.Record;
import com.website.spring.service.models.ServiceStatus;
import com.website.spring.service.repositories.MasterScheduleRepository;
import com.website.spring.service.repositories.RecordRepository;
import com.website.spring.service.security.details.UserDetailsImpl;
import com.website.spring.service.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.website.spring.service.transfer.UserDto.from;

@Controller
public class AdminInfoController {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private MasterScheduleRepository masterScheduleRepository;

    @GetMapping("/adminInfo")
    String getAdminPage(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = from(details.getUser());
        model.addAttribute("user", user);
        ArrayList<Record> recordsList = new ArrayList<>(recordRepository.findAll()); //
        model.addAttribute("records", recordsList); //
        model.addAttribute("allSchedule", masterScheduleRepository);
        return "adminInfo";
    }

    @PostMapping("/adminInfo")
    public String sendMasterInfo(Model model, String newTime, Long id, Long recordId) {
        if (recordId != null) {
            Record record = recordRepository.getRecordById(recordId);
            LocalDateTime previousTime = record.getLocalDateTime();
            String correctTime = newTime.substring(0, 19);
            recordRepository.updateTimeByIdRecord(correctTime, recordId);
            int duration = record.getServices().getDurationInMinutes();
            LocalDateTime dateTimeCorrect = LocalDateTime.parse(correctTime.replace(' ', 'T'));
            for (int i = 0; i < duration / 30; i++) {
                masterScheduleRepository.updateStatusByMasterId(record.getServiceStatus().toString(),
                        dateTimeCorrect.toString(),
                        record.getMasters().getId());
                masterScheduleRepository.updateStatusByMasterId(ServiceStatus.NONE.toString(),
                        previousTime.toString(),
                        record.getMasters().getId());
                dateTimeCorrect = dateTimeCorrect.plusMinutes(30);
                previousTime = previousTime.plusMinutes(30);
            }
            masterScheduleRepository.updateStatusByMasterId(record.getServiceStatus().toString(), correctTime, record.getMasters().getId());
            return "redirect:/adminInfo";
        }

        if (id != null && (masterScheduleRepository.findServiceStatusById(id).compareTo(ServiceStatus.OPEN) == 0)) {
            String time = masterScheduleRepository.getTime(id);
            Long masterId = masterScheduleRepository.getMasterId(id);
            masterScheduleRepository.updateStatusByMasterId(String.valueOf(ServiceStatus.CANCELED), time, masterId);
            recordRepository.updateRecordStatusMasterId(String.valueOf(ServiceStatus.CANCELED), time, masterId);
        }
        return "redirect:/adminInfo";
    }
}


