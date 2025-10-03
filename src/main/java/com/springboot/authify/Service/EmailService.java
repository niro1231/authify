// package com.springboot.authify.Service;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class EmailService {

//     private final JavaMailSender mailSender;

//     @Value("${spring.mail.properties.mail.smtp.from}")
//     private String fromEmail;

//     public void sendWelcomeEmail(String to, String name) {
//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setFrom(fromEmail);
//         message.setTo(to);
//         message.setSubject("Welcome to Authify, " + name + "!");
//         message.setText("Hello " + name + ",\n\nThanks for registering.");
//         mailSender.send(message);
//     }
// }
