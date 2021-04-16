package com.website.spring.service.models;

import com.website.spring.service.forms.UserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 35)
    private String firstName;
    @Column(nullable = false, length = 35)
    private String lastName;

    @Column(unique = true, length = 35, nullable = false)
    private String login;

    @Column(nullable = false)
    private String hashPassword;

    @Column(unique = true, length = 50)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Master> masters;


    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Record> records;


    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<MasterSchedule> masterSchedule;

    public static User from(UserForm form) {
        return User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .build();
    }
}
