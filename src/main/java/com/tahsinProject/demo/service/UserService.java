package com.tahsinProject.demo.service;

import com.tahsinProject.demo.entity.JournalEntry;
import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveUser(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info(user.getUserName());
        if (!user.getPassword().startsWith("$2a$")) { // BCrypt hashes start with $2a$
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUsersById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteUserById(ObjectId id){
        userRepository.deleteById(id);
    }

    public boolean deleteByUserName(String userName){
        User user = userRepository.findByUserName(userName);
        if(user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
