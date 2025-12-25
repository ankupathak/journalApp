package com.portfolio.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmail() {
        emailService.sendEmail("ankupathak004@gmail.com", "Hi from spring boot", "Hi, aap kaise ho?");
    }
}
