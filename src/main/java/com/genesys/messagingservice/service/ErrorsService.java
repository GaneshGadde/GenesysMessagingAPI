package com.genesys.messagingservice.service;

import com.genesys.messagingservice.entity.Errors;
import com.genesys.messagingservice.repository.ErrorsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorsService {
    @Autowired
    ErrorsRepository errorRepository;

    public Errors add(Errors error) {
        Errors addedError = errorRepository.save(error);
        return addedError;
    }

    public List<Errors> getAll() {
        List<Errors> errors = (List<Errors>) errorRepository.findAll();
        return errors;
    }

    public List<Errors> getAllByUserId(Long userId) {
        List<Errors> errors = errorRepository.findAllByUserId(userId);
        return errors;
    }
}
