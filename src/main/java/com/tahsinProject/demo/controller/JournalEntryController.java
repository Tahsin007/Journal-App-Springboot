package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
//    private final Map<Integer, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntries(){
        return journalEntryService.getEntries();
    }

    @PostMapping
    public ResponseEntity<?> createEntity(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create Journal Entry");
            error.put("details", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
         Optional<JournalEntry> journalEntry = journalEntryService.getEntriesById(myId);
         if(journalEntry.isPresent()){
             return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.getEntriesById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
