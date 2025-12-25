package com.portfolio.journalApp.controllers;

import com.portfolio.journalApp.entity.JournalEntry;
import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.service.JournalEntryService;
import com.portfolio.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        try {
            List<JournalEntry> journalEntries = journalEntryService.findEntriesByUserId(user.getId());
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } catch(Exception e) {
            log.error("Error while fetching journal entries: ", e);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry, authentication.getName());
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error in createEntry: ", e);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{journalEntryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalEntryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        try {
            JournalEntry journalEntry = journalEntryService.findEntryByIdAndUserId(journalEntryId, user.getId());
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } catch(Exception e) {
            log.error("Error in fetching journal entry of user: ", e);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{journalEntryId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalEntryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isDeleted = journalEntryService.deleteById(journalEntryId, authentication.getName());
        if(isDeleted) {
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{journalEntryId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId journalEntryId, @RequestBody JournalEntry newJournalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        JournalEntry journalEntry = journalEntryService.findEntryByIdAndUserId(journalEntryId, user.getId());
        if(journalEntry != null) {
            if(!newJournalEntry.getTitle().equals(""))
                journalEntry.setTitle(newJournalEntry.getTitle());
            if(newJournalEntry.getContent() != null && !newJournalEntry.getContent().equals(""))
                journalEntry.setContent(newJournalEntry.getContent());

            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
