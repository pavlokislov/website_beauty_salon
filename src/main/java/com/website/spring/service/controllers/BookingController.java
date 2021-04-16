package com.website.spring.service.controllers;

import com.website.spring.service.repositories.MasterScheduleRepository;
import com.website.spring.service.repositories.RecordRepository;
import com.website.spring.service.repositories.ServiceRepository;
import com.website.spring.service.repositories.UserRepository;
import com.website.spring.service.security.details.UserDetailsImpl;
import com.website.spring.service.transfer.BookingDto;
import com.website.spring.service.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.website.spring.service.transfer.UserDto.from;

@Controller
public class BookingController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private MasterScheduleRepository masterScheduleRepository;


    private BookingDto wholeBookingDto;

    @GetMapping("/booking")
    public String getBooking(Model model) {
        wholeBookingDto = new BookingDto();
        wholeBookingDto.setServicesList(serviceRepository.findAll());
        model.addAttribute("order", wholeBookingDto);
        return "booking";
    }

    @PostMapping("/booking")
    public String sendBookingStep1(Model model, BookingDto bookingDto) {
        this.wholeBookingDto.setServiceId(bookingDto.getServiceId());
        Long id = bookingDto.getServiceId();
        wholeBookingDto.setServiceId(id);
        String nameService = serviceRepository.findNameByID(id);
        this.wholeBookingDto.setService(nameService);
        Integer price = serviceRepository.findPriceByServiceId(id);
        this.wholeBookingDto.setPrice(price);
        return "redirect:/booking2";
    }

    @GetMapping("/booking2")
    public String getBookingStep2(Model model) {
        Long serviceId = wholeBookingDto.getServiceId();
        List<Object[]> list = userRepository.findMasterIdByServiceId(serviceId);
        wholeBookingDto.setMastersIdNames(new ArrayList(list));
        model.addAttribute("order", wholeBookingDto);
        return "booking2";
    }

    @PostMapping("/booking2")
    public String sendBooking2(Model model, BookingDto bookingDto) {
        wholeBookingDto.setMasterId(bookingDto.getMasterId());

        Long masterId = userRepository.findUserIdByMasterId(wholeBookingDto.getMasterId());
        wholeBookingDto.setMasterId(masterId);
        wholeBookingDto.setMaserName(userRepository.findFirstNameByMasterId(masterId));
        return "redirect:/booking3";
    }


    @GetMapping("/booking3")
    public String getBookingStep3(Model model) {
        Long masterId = wholeBookingDto.getMasterId();
        ArrayList<LocalDateTime> timeArrayList = new ArrayList<LocalDateTime>(masterScheduleRepository.
                findAvailableTime(masterId));
        wholeBookingDto.setAvailableTime(timeArrayList);
        model.addAttribute("order", wholeBookingDto);
        return "booking3";
    }

    @PostMapping("/booking3")
    public String sendBooking3(Model model, BookingDto bookingDto) {
        this.wholeBookingDto.setTime(bookingDto.getTime());
        model.addAttribute("order", wholeBookingDto);
        return "redirect:/booking4";
    }


    @GetMapping("/booking4")
    public String getBookingStep4(Model model) {
        model.addAttribute("order", wholeBookingDto);
        return "booking4";
    }


    @PostMapping("/booking4")
    public String sendBooking4(Model model, Authentication authentication) {
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = from(details.getUser());
        recordRepository.insertNewRecord(wholeBookingDto.getTime(),
                wholeBookingDto.getMasterId(),
                wholeBookingDto.getServiceId(), user.getId());
        LocalDateTime openTime = LocalDateTime.parse(wholeBookingDto.getTime().replace(' ', 'T'));
        Integer durationService = serviceRepository.findDurationServiceByMasterIdAndTime(wholeBookingDto.getTime(), wholeBookingDto.getMasterId());
        for (int i = 0; i < durationService / 30; i++) {
            masterScheduleRepository.updateMasterSchedule(String.valueOf(openTime), wholeBookingDto.getMasterId());
            openTime = openTime.plusMinutes(30);
        }
        return "redirect:/booking/payment";
    }
}
