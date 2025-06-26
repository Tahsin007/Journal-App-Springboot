package com.tahsinProject.demo.repository;

import com.tahsinProject.demo.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
    Void deleteByUserName(String userName);
}
