package com.genesys.messagingservice.service;

import com.genesys.messagingservice.entity.Action;
import com.genesys.messagingservice.repository.ActionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {
    @Autowired
    ActionRepository actionRepository;

    public Action add(Action action) {
        return actionRepository.save(action);
    }

    public List<Action> getAll() {
        List<Action> actions = (List<Action>) actionRepository.findAll();
        return actions;
    }

    public List<Action> getAllByUserId(Long userId) {
        List<Action> actions = actionRepository.findAllByUserId(userId);
        return actions;
    }

    public List<Action> getAllByUserIdAndActionType(Long userId, Action.ActionType actionType) {
        List<Action> actions = actionRepository.findAllByUserIdAndActionType(userId, actionType.name());
        return actions;
    }
}
