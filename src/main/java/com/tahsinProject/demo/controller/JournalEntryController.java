package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
//    private final Map<Integer, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ResponseEntity<?> getAllEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JournalEntry> allEntries = journalEntryService.getEntries(authentication.getName());
        if(allEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allEntries, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            journalEntryService.saveEntry(journalEntry, authentication.getName());
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create Journal Entry");
            error.put("details", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("{entryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId entryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         JournalEntry journalEntry = journalEntryService.getEntriesByUserName(entryId, authentication.getName());
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);

//        if(journalEntry.){
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{journalId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId journalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntryService.deleteById(journalId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JournalEntry old = journalEntryService.getEntriesByUserName(myId, authentication.getName());
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
        old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
        journalEntryService.updateEntriesById(old, authentication.getName(),myId);
        return new ResponseEntity<>(old, HttpStatus.CREATED);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
