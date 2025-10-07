package com.edigest.Journal.service;

import com.edigest.Journal.entity.JournalEntry;
import com.edigest.Journal.entity.User;
import com.edigest.Journal.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);

    @Transactional
    public void saveEntry(JournalEntry myEntry,String userName){
        try{
            User user = userService.findByUserName(userName);
            JournalEntry saved = journalRepository.save(myEntry);
            user.getJournals().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            logger.info("Error");
            throw new RuntimeException("An error occurred while saving entry");
        }
    }

    public void saveEntry(JournalEntry journal){
        journalRepository.save(journal);
    }

    @Transactional
    public boolean deleteById(String userName, ObjectId id){
        boolean removed = false;
        try{
            User user = userService.findByUserName(userName);
            removed = user.getJournals().removeIf(x -> x.getObjectId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting journal");
        }
        return removed;
    }

    public Optional<JournalEntry> findByJournalId(ObjectId myId){
        return journalRepository.findById(myId);
    }
}
