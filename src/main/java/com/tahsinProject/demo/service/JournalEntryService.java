package com.tahsinProject.demo.service;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, ObjectId userId){
        User userDB = userService.getUsersById(userId).orElse(null);
        journalEntryRepository.save(journalEntry);
        assert userDB != null;
        List<JournalEntry> journalEntries = userDB.getJournalEntries();
        journalEntries.add(journalEntry);
        userService.saveUser(userDB);
    }

    public List<JournalEntry> getEntries(ObjectId userId){
        User currentUser = userService.getUsersById(userId).orElse(null);
        assert currentUser != null;
        return currentUser.getJournalEntries();
//        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntriesById(ObjectId id, ObjectId userId){
        Optional<User> currentUser = userService.getUsersById(userId);
        if(currentUser.isPresent()){
            return currentUser.get().getJournalEntries().get(0);
        }
        return null;
//        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId journalId, ObjectId userId){
        User curentUser = userService.getUsersById(userId).orElse(null);
        List<JournalEntry> journalEntries = curentUser.getJournalEntries();
        journalEntries.removeIf(x -> x.getId().equals(journalId));
        userService.saveUser(curentUser);
        journalEntryRepository.deleteById(journalId);
    }

    public void updateEntriesById(JournalEntry journalEntry, ObjectId userId, ObjectId journalId) {
        User userDB = userService.getUsersById(userId).orElse(null);
        if (userDB == null) {
            // Handle user not found, e.g., throw new UserNotFoundException("User with ID " + userId + " not found");
            return;
        }

        List<JournalEntry> journalEntries = userDB.getJournalEntries();
        boolean foundAndUpdated = false;

        for (int i = 0; i < journalEntries.size(); i++) {
            JournalEntry existingEntry = journalEntries.get(i);
            if (existingEntry.getId().equals(journalId)) { // Assuming JournalEntry has an 'id' field of type ObjectId
                existingEntry.setTitle(journalEntry.getTitle());
                existingEntry.setDescription(journalEntry.getDescription());
                journalEntryRepository.save(existingEntry);
                foundAndUpdated = true;
                break;
            }
        }

        if (!foundAndUpdated) {
            System.out.println("Journal entry with ID " + journalId + " not found for user " + userId);
        }

        userService.saveUser(userDB); // Save the user to persist changes to the journalEntries list (though in this case, individual entry is saved)
    }
}
