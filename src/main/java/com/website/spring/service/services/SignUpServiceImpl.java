package com.website.spring.service.services;

import com.website.spring.service.forms.UserForm;
import com.website.spring.service.models.Role;
import com.website.spring.service.models.State;
import com.website.spring.service.models.User;
import com.website.spring.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        User user = User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .email(userForm.getEmail())
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();

        userRepository.save(user);

    }
}
