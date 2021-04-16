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
@Table(name = "master")
public class Master {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(scale = 2, precision = 1)
    private Double rating;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private Service service;


    //change//
    @OneToMany(mappedBy = "masters", fetch = FetchType.LAZY)
    private List<Record> records;
    //change//

//    @OneToMany(mappedBy = "mastersId", fetch = FetchType.LAZY)
//    private List<MasterSchedule> masterSchedule;

}
