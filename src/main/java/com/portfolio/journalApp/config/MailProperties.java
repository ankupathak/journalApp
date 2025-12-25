package com.portfolio.journalApp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.myApp.mail")
@Getter
public class MailProperties {

    @JsonProperty("from")
    private String mailFrom;
}
