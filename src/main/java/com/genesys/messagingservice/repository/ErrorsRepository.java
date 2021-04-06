package com.genesys.messagingservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.genesys.messagingservice.entity.Errors;

import java.util.List;

public interface ErrorsRepository extends CrudRepository<Errors, Long> {
    List<Errors> findAllByUserId(Long userId);
}
