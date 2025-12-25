package com.portfolio.journalApp.repository;

import com.portfolio.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    public JournalEntry findByIdAndUserId(ObjectId id, ObjectId userId);

    public List<JournalEntry> findByUserId(ObjectId userId);

    public void deleteByIdAndUserId(ObjectId id, ObjectId userId);
}
