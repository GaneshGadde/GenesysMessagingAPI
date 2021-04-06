package com.genesys.messagingservice.controller;

import com.genesys.messagingservice.entity.Action;
import com.genesys.messagingservice.service.ActionService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
public class ActionController {
    @Autowired
    ActionService actionService;

    @ApiOperation("Get all actions")
    @GetMapping("/all")
    public ResponseEntity<List<Action>> getAllActions() {
        List<Action> actions = actionService.getAll();
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @ApiOperation("Get all user actions by given id")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Action>> getAllActionsUserById(@PathVariable Long id) {
        List<Action> actions = actionService.getAllByUserId(id);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @ApiOperation("Get all user actions by given id and action type")
    @GetMapping("/all/{id}/action-type")
    public ResponseEntity<List<Action>> getAllActionsUserById(@PathVariable Long id, @RequestParam Action.ActionType actionType) {
        List<Action> actions = actionService.getAllByUserIdAndActionType(id, actionType);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }
}
