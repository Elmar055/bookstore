package com.ingress.bookstore.service;

import com.ingress.bookstore.model.User;
import com.ingress.bookstore.repository.UserRepository;
import com.ingress.bookstore.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService
{

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        return JwtUserDetails.create(user);

    }

    public UserDetails loadById(Long id)
    {
        User user = userRepository.findById(id).get();
        return JwtUserDetails.create(user);
    }

    public UserDetails loadByEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        return JwtUserDetails.create(user);
    }

}

