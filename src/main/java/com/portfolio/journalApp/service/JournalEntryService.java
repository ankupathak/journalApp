package com.portfolio.journalApp.service;

import com.portfolio.journalApp.entity.JournalEntry;
import com.portfolio.journalApp.entity.User;
import com.portfolio.journalApp.repository.JournalEntryRepository;
import com.portfolio.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public boolean saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setUserId(user);
            journalEntryRepository.save(journalEntry);
            return true;
        } catch(Exception e) {
            log.error("Error while saving jounral entry in service: ", e);
        }

        return false;
    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("error while updating entry "+e);
        }
    }

    public Optional<JournalEntry> findById(ObjectId journalEntryId) {
        return journalEntryRepository.findById(journalEntryId);
    }

    public JournalEntry findEntryByIdAndUserId(ObjectId id, ObjectId userId) {
        return journalEntryRepository.findByIdAndUserId(id, userId);
    }

    public List<JournalEntry> findEntriesByUserId(ObjectId userId) {
        return journalEntryRepository.findByUserId(userId);
    }

    @Transactional
    public boolean deleteById(ObjectId journalEntryId, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntryRepository.deleteByIdAndUserId(journalEntryId, user.getId());
            return true;
        } catch(Exception e) {
            log.error("Error while deleting entry: ", e);
        }

        return false;
    }
}
