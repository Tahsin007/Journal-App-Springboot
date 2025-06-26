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

    @GetMapping("{userId}")
    public List<JournalEntry> getAllEntries(@PathVariable ObjectId userId){
        return journalEntryService.getEntries(userId);
    }

    @PostMapping("{userId}")
    public ResponseEntity<?> createEntity(@RequestBody JournalEntry journalEntry, @PathVariable ObjectId userId) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry, userId);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create Journal Entry");
            error.put("details", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{entryId}/{userId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId entryId, @PathVariable ObjectId userId){
         JournalEntry journalEntry = journalEntryService.getEntriesById(entryId, userId);
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);

//        if(journalEntry.){
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{journalId}/{userId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId journalId, @PathVariable ObjectId userId){
        journalEntryService.deleteById(journalId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}/{userId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry,@PathVariable ObjectId userId) {
        JournalEntry old = journalEntryService.getEntriesById(myId, userId);
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
        old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
        journalEntryService.updateEntriesById(old, userId,myId);
        return new ResponseEntity<>(old, HttpStatus.CREATED);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
