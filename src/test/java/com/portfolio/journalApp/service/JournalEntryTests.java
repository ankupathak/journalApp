package com.portfolio.journalApp.service;

import com.portfolio.journalApp.entity.JournalEntry;
import com.portfolio.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JournalEntryTests {

    @Autowired
    private JournalEntryService journalEntryService;

    @Test
    public void saveEntryTest() {
//        User user = User.builder().userName("test").password("test").build();
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle("Dummy Failed");
        journalEntry.setContent("Dummy D");
        assertNotNull(journalEntryService.saveEntry(journalEntry, "test"));
    }
}
