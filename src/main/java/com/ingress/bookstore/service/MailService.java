package com.ingress.bookstore.service;

import com.ingress.bookstore.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService
{
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public String sendMail()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo("student@gmail.com");
        message.setSubject("New Book");
        message.setText("Hello. New book is published.");
        mailSender.send(message);
        return "Message sent";
    }

    public String sendMail(List<Student> followers) {
        for (Student follower : followers) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@gmail.com");
            message.setTo(follower.getUser().getEmail());
            message.setSubject("New Book");
            message.setText("Hello. New book is published.");
            mailSender.send(message);
        }

        return "Messages sent";
    }
}
