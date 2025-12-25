package com.portfolio.journalApp.controllers;

import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("All Ok, Everything working fine", HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            user.getRoles().add("USER");
            user.setPassword(userService.encodePassword(user.getPassword()));
            user.setDate(LocalDateTime.now());
            Optional<User> savedUser = userService.saveUser(user);
            if(savedUser.isPresent()) {
                kafkaTemplate.send("sendEmailTopic", "Send Email for "+user.getUserName());
                return new ResponseEntity<>(savedUser.get(), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Error at create user: ",e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
