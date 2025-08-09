package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.service.JournalEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all journal entries for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved journal entries"),
            @ApiResponse(responseCode = "404", description = "No journal entries found")
    })
    @GetMapping
    public ResponseEntity<?> getAllEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JournalEntry> allEntries = journalEntryService.getEntries(authentication.getName());
        if(allEntries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allEntries, HttpStatus.OK);

    }

    @Operation(summary = "Create a new journal entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Journal entry created successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to create journal entry")
    })
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


    @Operation(summary = "Get a journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved journal entry"),
            @ApiResponse(responseCode = "404", description = "Journal entry not found")
    })
    @GetMapping("{entryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@Parameter(description = "ID of the journal entry to retrieve") @PathVariable ObjectId entryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         JournalEntry journalEntry = journalEntryService.getEntriesByUserName(entryId, authentication.getName());
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }

    @Operation(summary = "Delete a journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Journal entry deleted successfully")
    })
    @DeleteMapping("{journalId}")
    public ResponseEntity<?> deleteEntryById(@Parameter(description = "ID of the journal entry to delete") @PathVariable ObjectId journalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntryService.deleteById(journalId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a journal entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Journal entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Journal entry not found")
    })
    @PutMapping("{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@Parameter(description = "ID of the journal entry to update") @PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JournalEntry old = journalEntryService.getEntriesByUserName(myId, authentication.getName());
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
        old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
        journalEntryService.updateEntriesById(old, authentication.getName(),myId);
        return new ResponseEntity<>(old, HttpStatus.CREATED);
    }
}
