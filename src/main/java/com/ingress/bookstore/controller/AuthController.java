package com.ingress.bookstore.controller;

import com.ingress.bookstore.model.Author;
import com.ingress.bookstore.model.Student;
import com.ingress.bookstore.model.User;
import com.ingress.bookstore.request.UserLoginRequest;
import com.ingress.bookstore.request.UserRegisterRequest;
import com.ingress.bookstore.security.JwtTokenProvider;
import com.ingress.bookstore.service.AuthorService;
import com.ingress.bookstore.service.StudentService;
import com.ingress.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController
{
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private StudentService studentService;
    private AuthorService authorService;
    private PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          UserService userService, PasswordEncoder passwordEncoder,
                          StudentService studentService, AuthorService authorService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
        this.authorService = authorService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest loginRequest)
    {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        return ResponseEntity.ok("Bearer " + jwtToken);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest registerRequest)
    {
        if (userService.getOneUserByUserName(registerRequest.getUsername()) != null)
        {
            return new ResponseEntity<>("Username already exist.", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        userService.saveOneUser(user);

        if ("STUDENT".equals(registerRequest.getRole()))
        {
            Student student = new Student();
            student.setBirthDate(registerRequest.getBirthdate());
            student.setName(registerRequest.getName());
            student.setUser(user);
            studentService.saveStudent(student);
        }
        if ("AUTHOR".equals(registerRequest.getRole()))
        {
            Author author = new Author();
            author.setBirthdate(registerRequest.getBirthdate());
            author.setName(registerRequest.getName());
            author.setUser(user);
            authorService.saveAuthor(author);
        }


        return new ResponseEntity<>("Registered successfully", HttpStatus.OK);
    }

}
