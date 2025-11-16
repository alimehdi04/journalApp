package com.autisheimer.journalApp.controller;

import com.autisheimer.journalApp.entity.JournalEntry;
import com.autisheimer.journalApp.entity.User;
import com.autisheimer.journalApp.service.JournalEntryService;
import com.autisheimer.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntiresOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, username);
            return new ResponseEntity<JournalEntry>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{currId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId currId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(currId)).collect(Collectors.toList());
        if(!journalEntries.isEmpty()){
            Optional<JournalEntry> entry = journalEntryService.findById(currId);
            if(entry.isPresent()){
                return new ResponseEntity<JournalEntry>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{currId}")
    public ResponseEntity<?> deleteJournalEntryById( @PathVariable ObjectId currId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = journalEntryService.deleteById(currId, userName);
        if(deleted)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{currId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(
            @PathVariable ObjectId currId,
            @RequestBody JournalEntry entry
    ){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUsername(userName);
            List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(currId)).collect(Collectors.toList());
            if(!journalEntries.isEmpty()){
                Optional<JournalEntry> existing = journalEntryService.findById(currId);
                if(existing.isPresent()){

                    JournalEntry old = existing.get();
                    if(existing != null){
                        old.setTitle(entry.getTitle()!=null && !entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
                        old.setContent(entry.getContent()!=null && !entry.getContent().equals("") ? entry.getContent() : old.getContent());
                        journalEntryService.saveEntry(old);
                        return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
                    }
                }
            }


            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}