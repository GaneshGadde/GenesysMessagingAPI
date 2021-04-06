package com.genesys.messagingservice.controller;

import com.genesys.messagingservice.entity.Errors;
import com.genesys.messagingservice.entity.Message;
import com.genesys.messagingservice.entity.User;
import com.genesys.messagingservice.exception.NoMessageFoundException;
import com.genesys.messagingservice.exception.NoUserFoundException;
import com.genesys.messagingservice.request.MessageRequest;
import com.genesys.messagingservice.request.MessageUpdateRequest;
import com.genesys.messagingservice.security.CustomUserDetailsService;
import com.genesys.messagingservice.service.ErrorsService;
import com.genesys.messagingservice.service.MessageService;
import com.genesys.messagingservice.service.OperatorService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    ErrorsService errorsService;

    @ApiOperation("Send message to a user from the current user")
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest) throws NoUserFoundException {
        User currentUser = customUserDetailsService.getCurrentUser();
        try {
            Message addedMessage = operatorService.sendMessage(messageRequest.toMessage(currentUser.getId(), currentUser.getUsername()));
            if (addedMessage == null) {
                return new ResponseEntity<>("Couldn't send the message", HttpStatus.OK);
            }
            return new ResponseEntity<>(addedMessage, HttpStatus.OK);
        } catch (NoUserFoundException e) {
            errorsService.add(Errors.newError(messageRequest.getReceiverId(), null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @ApiOperation("Get all messages of the current user")
    @GetMapping("/current/")
    public ResponseEntity<List<Message>> getAllMessagesOfCurrentUser() throws NoUserFoundException {
        User currentUser = customUserDetailsService.getCurrentUser();
        List<Message> messages = messageService.getAllById(currentUser.getId());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @ApiOperation("Get the dialog of the current user by given user id")
    @GetMapping("/current/{userId}")
    public ResponseEntity<List<Message>> getDialogOfCurrentUserByUserId(@PathVariable Long userId) throws NoUserFoundException {
        User currentUser = customUserDetailsService.getCurrentUser();
        List<Message> messages = messageService.getDialog(currentUser.getId(), userId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @ApiOperation("Update message by given id")
    @PutMapping
    public ResponseEntity<?> updateMessageById(@RequestBody MessageUpdateRequest request) {
        try {
            User user = customUserDetailsService.getCurrentUser();
            Message messageToUpdate = messageService.get(request.getId());
            if (!messageToUpdate.getSenderId().equals(user.getId())) {
                errorsService.add(Errors.newError(request.getId(), null, "You don't have permission to update this message."));
                return new ResponseEntity<>("You don't have permission to update this message.", HttpStatus.OK);
            }
            Message updatedMessage = messageService.update(request.toMessage());
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (Exception e) {
            errorsService.add(Errors.newError(request.getId(), null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @ApiOperation("Delete message by given id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Long id) {
        try {
            Message messageToDelete = messageService.get(id);
            messageService.delete(messageToDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoMessageFoundException e) {
            errorsService.add(Errors.newError(id, null, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
