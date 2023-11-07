package com.ingress.bookstore.controller;

import com.ingress.bookstore.model.User;
import com.ingress.bookstore.request.UserLoginRequest;
import com.ingress.bookstore.request.UserRegisterRequest;
import com.ingress.bookstore.security.JwtTokenProvider;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.StudentService;
import com.ingress.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private StudentService studentService;

    @Mock
    private AuthorService authorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(jwtTokenProvider.generateJwtToken(authentication)).thenReturn("testToken");

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bearer testToken", response.getBody());
    }

    @Test
    public void testRegisterStudent() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("testPassword");
        registerRequest.setRole("STUDENT");
        registerRequest.setBirthdate(LocalDate.of(2000,01,01));
        registerRequest.setName("Test Student");

        when(userService.getOneUserByUserName(registerRequest.getUsername())).thenReturn(null);

        User savedUser = new User();
        savedUser.setId(1L);
        when(userService.saveOneUser(any(User.class))).thenReturn(savedUser);

        ResponseEntity<String> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registered successfully", response.getBody());
    }

    @Test
    public void testRegisterAuthor() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setUsername("testUser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("testPassword");
        registerRequest.setRole("AUTHOR");
        registerRequest.setBirthdate(LocalDate.of(2000,01,01));
        registerRequest.setName("Test Author");

        when(userService.getOneUserByUserName(registerRequest.getUsername())).thenReturn(null);

        User savedUser = new User();
        savedUser.setId(1L);
        when(userService.saveOneUser(any(User.class))).thenReturn(savedUser);

        ResponseEntity<String> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registered successfully", response.getBody());
    }
}