package com.portfolio.journalApp.controllers;

import com.portfolio.journalApp.api.response.QuoteResponse;
import com.portfolio.journalApp.entity.JournalEntry;
import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.service.JournalEntryService;
import com.portfolio.journalApp.service.QuoteService;
import com.portfolio.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuoteService quoteService;

    @GetMapping
    public ResponseEntity<User> fetchUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.findByUserName(authentication.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception e) {
            log.error("Fetching authenticated user details: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        if(user != null) {
            user.setUserName(updatedUser.getUserName());
            user.setPassword(updatedUser.getPassword());
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("greet-with-quote")
    public ResponseEntity<String> greeting() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String quote = quoteService.getQuote();
        return new ResponseEntity<>("Hi "+authentication.getName()+", \n"+quote, HttpStatus.OK);
    }
}
