package com.portfolio.journalApp.service;

import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameTest() {

        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(
                (com.portfolio.journalApp.entity.User) User.builder().userName("ankit").password("ankit").roles(new ArrayList<>()).build()
        );
        UserDetails user = userDetailsService.loadUserByUsername("admin");
        Assertions.assertNotNull(user);
    }
}
