package com.genesys.messagingservice.controller;

import com.genesys.messagingservice.entity.Errors;
import com.genesys.messagingservice.service.ErrorsService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/errors")
public class ErrorsController {
    @Autowired
    ErrorsService errorsService;

    @ApiOperation("Get all errors")
    @GetMapping("/all")
    public ResponseEntity<List<Errors>> getAllActions() {
        List<Errors> errors = errorsService.getAll();
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    @ApiOperation("Get all user errors by given id")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Errors>> getAllActionsUserById(@PathVariable Long id) {
        List<Errors> errors = errorsService.getAllByUserId(id);
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }
}
