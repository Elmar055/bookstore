package com.ingress.bookstore.service;

import com.ingress.bookstore.model.User;
import com.ingress.bookstore.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User saveOneUser(User newUser)
    {
        return userRepository.save(newUser);
    }
    public User getOneUserByUserName(String username)
    {
        return userRepository.findByUsername(username);
    }
}
