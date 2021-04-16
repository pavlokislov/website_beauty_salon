package com.website.spring.service.security.details;

import com.website.spring.service.models.Role;
import com.website.spring.service.models.User;
import com.website.spring.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsesDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //change later
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        Optional<User> userCandidate = userRepository.findOneByLogin(login);
//        if(userCandidate.isPresent()){
//            return new UserDetailsImpl(userCandidate.get());
//        }
//        else throw  new IllegalArgumentException("User not found"); //Replace
        return new UserDetailsImpl(userRepository.findOneByLogin(login)
                .orElseThrow(IllegalArgumentException::new));

    }
}
