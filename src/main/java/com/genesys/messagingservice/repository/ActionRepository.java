package com.genesys.messagingservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.genesys.messagingservice.entity.Action;

import java.util.List;

public interface ActionRepository extends CrudRepository<Action, Long> {
    List<Action> findAllByUserId(Long userId);

    List<Action> findAllByUserIdAndActionType(Long userId, String actionType);
}
