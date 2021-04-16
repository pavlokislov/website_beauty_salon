package com.website.spring.service.transfer;

import com.website.spring.service.models.Master;
import com.website.spring.service.models.Service;
import com.website.spring.service.repositories.ServiceRepository;
import com.website.spring.service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BookingDto {
    @Autowired
    private  ServiceRepository serviceRepository;
    private Long masterId;
    private List<Service> servicesList;
    private Long serviceId;
    private String service;
    private Integer price;
    private ArrayList<String> mastersIdNames;
    private String maserName;
    private String time;
    private ArrayList<LocalDateTime> availableTime;

    public BookingDto() {

    }

}
