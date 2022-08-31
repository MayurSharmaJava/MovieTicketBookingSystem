package com.booking.config;

import com.booking.entity.User;
import com.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class BookingSystemUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if(users.isEmpty()){
            throw new UsernameNotFoundException("User "+username+" is not registered user.");
        }
        return new SecurityUser(users.get(0));
    }
}
