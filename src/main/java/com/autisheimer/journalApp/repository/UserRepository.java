package com.autisheimer.journalApp.repository;

import com.autisheimer.journalApp.entity.JournalEntry;
import com.autisheimer.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    Void deleteByUsername(String username);
}