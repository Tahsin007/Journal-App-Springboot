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

    public void saveEntry(JournalEntry journalEntry, String userName){
        User userDB = userService.findByUserName(userName);
        journalEntryRepository.save(journalEntry);
//        assert userDB != null;
        List<JournalEntry> journalEntries = userDB.getJournalEntries();
        journalEntries.add(journalEntry);
        userService.saveUser(userDB);
    }

    public List<JournalEntry> getEntries(String userName){
        User currentUser = userService.findByUserName(userName);
//        assert currentUser != null;
        return currentUser.getJournalEntries();
//        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntriesByUserName(ObjectId id, String userName){
        User currentUser = userService.findByUserName(userName);
        return currentUser.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElse(null);
//        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId journalId, String userName){
        User curentUser = userService.findByUserName(userName);
        List<JournalEntry> journalEntries = curentUser.getJournalEntries();
        journalEntries.removeIf(x -> x.getId().equals(journalId));
        userService.saveUser(curentUser);
        journalEntryRepository.deleteById(journalId);
    }

    public void updateEntriesById(JournalEntry journalEntry, String userName, ObjectId journalId) {
        User userDB = userService.findByUserName(userName);
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
            System.out.println("Journal entry with ID " + journalId + " not found for user " + userName);
        }

        userService.saveUser(userDB); // Save the user to persist changes to the journalEntries list (though in this case, individual entry is saved)
    }
}
