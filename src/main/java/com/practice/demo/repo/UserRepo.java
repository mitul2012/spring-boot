package com.practice.demo.repo;

import com.practice.demo.data.User;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<User,Integer> {

    @Query(value = "{}",fields = "{password:0,_id:0}")
    List<User> findUserByIdWithProjection(Integer id);
    User findByUserName(String userName);
}
