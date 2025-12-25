package com.portfolio.journalApp.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumerService {

    @KafkaListener(topics = "sendEmailTopic", groupId = "emailGroup")
    public void processMessages(String content) {
        System.out.println("Message received: ");
        System.out.println(content);
    }
}
