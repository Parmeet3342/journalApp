package com.edigest.Journal.service;

import com.edigest.Journal.entity.JournalEntry;
import com.edigest.Journal.entity.User;
import com.edigest.Journal.repository.JournalRepository;
import com.edigest.Journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalRepository journalRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveNewUser(User user){
       try{
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("USER"));
           userRepository.save(user);
       } catch (Exception e) {
           logger.info("error");
           throw new RuntimeException(e);
       }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteUserById(ObjectId myId,String userName){
        User user = userRepository.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournals();
        for (JournalEntry journals:journalEntries){
            journalRepository.deleteById(journals.getObjectId());
        }
        userRepository.deleteById(myId);
    }
}
