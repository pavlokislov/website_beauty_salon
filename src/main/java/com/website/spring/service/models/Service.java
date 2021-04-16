package com.website.spring.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(nullable = false, length = 35)
    String name;
    @Column(nullable = false)
    Integer price;
    @Column(nullable = false)
    Integer durationInMinutes;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<Master> masters;

    @OneToMany(mappedBy = "services", fetch = FetchType.LAZY)
    private List<Record> records;
}