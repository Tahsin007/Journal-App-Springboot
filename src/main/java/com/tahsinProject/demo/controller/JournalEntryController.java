package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
    public JournalEntry createEntity(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getEntriesById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public String deleteEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
        return "{myId} is deleted";
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getEntriesById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription(): old.getDescription());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
