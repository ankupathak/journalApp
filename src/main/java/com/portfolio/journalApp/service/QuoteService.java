package com.portfolio.journalApp.service;

import com.portfolio.journalApp.api.response.QuoteResponse;
import com.portfolio.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class QuoteService {

    private static final String apiUrl = "https://thequoteshub.com/api/";

    @Autowired
    private RestTemplate restTemplate;

    public String getQuote() {
        try {
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.set("key", "value");
//            User user = User.builder().userName("aa").password("aa").build();
//            HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
            ResponseEntity<QuoteResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, QuoteResponse.class);
            return response.getBody().getText();
        } catch(Exception e) {
            log.error("error in Quote service ", e);
        }

        return "";
    }


}
