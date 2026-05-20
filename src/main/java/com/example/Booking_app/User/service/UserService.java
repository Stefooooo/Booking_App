package com.example.Booking_app.User.service;

import com.example.Booking_app.User.model.User;
import com.example.Booking_app.User.model.UserRole;
import com.example.Booking_app.User.reposotory.UserRepository;
import com.example.Booking_app.exceptions.UsernameAlreadyExists;
import com.example.Booking_app.security.UserAuthenticationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("Username not found!"));
        return new UserAuthenticationData(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public User register(User userFromRegisterPage){

        if(userRepository.findByUsername(userFromRegisterPage.getUsername()).isPresent()){
            throw new UsernameAlreadyExists("A user with a username [%s] already exists.".formatted(userFromRegisterPage.getUsername()));
        }

        LocalDateTime now = LocalDateTime.now();

        userFromRegisterPage.setRole(UserRole.USER);
        userFromRegisterPage.setCreatedOn(now);
        userFromRegisterPage.setUpdatedOn(now);

        return userRepository.save(userFromRegisterPage);
    }
}
