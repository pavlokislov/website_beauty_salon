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
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime localDateTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "master_id")
    private Master masters;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private Service services;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default 'UNPAID'")
    private PaymentStatus paymentStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(10) default 'OPEN'")
    private ServiceStatus serviceStatus;
}

enum PaymentStatus {
    UNPAID, FAILED, EXPIRED, SENT, PAID, REFUNDING, REFUNDED
}
