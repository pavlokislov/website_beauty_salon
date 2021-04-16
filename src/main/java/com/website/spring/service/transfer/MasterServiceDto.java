package com.website.spring.service.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MasterServiceDto {
    private String firstName;
    private String rating;
    private String nameService;
    private String price;

    public static MasterServiceDto from(List<String> list) {
        return MasterServiceDto.builder()
                .firstName(list.get(0))
                .rating(list.get(1))
                .nameService(list.get(2))
                .price(list.get(3))
                .build();
    }
}
