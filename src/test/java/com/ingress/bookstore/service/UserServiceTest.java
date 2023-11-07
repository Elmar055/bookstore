package com.ingress.bookstore.service;


import com.ingress.bookstore.model.User;
import com.ingress.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveOneUser() {
        User userToSave = new User();

        userService.saveOneUser(userToSave);

        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void testGetOneUserByUserName() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getOneUserByUserName(username);

        assertEquals(user, result);
    }
}
