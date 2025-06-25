package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final Map<Integer, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntries(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntity(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(),journalEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Integer myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deleteEntryById(@PathVariable Integer myId){
        return journalEntries.remove(myId);
    }

    @PutMapping("id/{myId")
    public JournalEntry updateEntryById(@PathVariable Integer myId, @RequestBody JournalEntry journalEntry){
        return journalEntries.put(myId,journalEntry);
    }
}
