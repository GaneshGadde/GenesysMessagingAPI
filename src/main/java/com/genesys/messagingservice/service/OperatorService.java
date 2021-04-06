package com.genesys.messagingservice.service;

import com.genesys.messagingservice.entity.Action;

import com.genesys.messagingservice.entity.Message;
import com.genesys.messagingservice.entity.User;
import com.genesys.messagingservice.exception.NoUserFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    ActionService actionService;

    public User addUser(User newUser) {
        User user = null;
        try {
            user = userService.getByUsername(newUser.getUsername());
            actionService.add(Action.newAction(user, Action.ActionType.ADD_USER, Action.ActionStatus.FAILED, "A user with this username already exists."));
            return null;
        } catch (NoUserFoundException e) {
            user = userService.add(newUser);
            actionService.add(Action.newAction(user, Action.ActionType.ADD_USER, Action.ActionStatus.SUCCESSFUL, null));
            return user;
        }
    }

    public Message sendMessage(Message message) throws NoUserFoundException {
        User user = userService.get(message.getSenderId());
        actionService.add(Action.newAction(user, Action.ActionType.SEND_MESSAGE, Action.ActionStatus.SUCCESSFUL, null));
        return messageService.add(message);

    }

    public void deleteUser(User user) {
        messageService.deleteAllByReceiverId(user.getId());
        userService.delete(user);
    }
}
