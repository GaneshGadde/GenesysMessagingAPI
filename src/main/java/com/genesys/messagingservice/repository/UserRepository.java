package com.genesys.messagingservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.genesys.messagingservice.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
}