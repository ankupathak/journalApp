package com.portfolio.journalApp.controllers.admin;

import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserAdminController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> fetchAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> fetchUserByName(@PathVariable String userName) {
        try {
            return new ResponseEntity<>(userService.findByUserName(userName), HttpStatus.OK);
        } catch(Exception e) {
            log.error("Error while fetching user by username: ", e);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
