package com.website.spring.service.models;

import com.website.spring.service.time.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "master_schedule")
public class MasterSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default 'NONE'")
    private ServiceStatus serviceStatus;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime localDateTime;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;


}
