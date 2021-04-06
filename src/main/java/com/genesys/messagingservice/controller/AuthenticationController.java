package com.genesys.messagingservice.controller;

import com.genesys.messagingservice.entity.Action;
import com.genesys.messagingservice.entity.User;
import com.genesys.messagingservice.exception.NoUserFoundException;
import com.genesys.messagingservice.request.LoginRequest;
import com.genesys.messagingservice.security.CustomUserDetailsService;
import com.genesys.messagingservice.service.ActionService;
import com.genesys.messagingservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ActionService actionService;

    @Autowired
    UserService userService;

    @ApiOperation("Login to the system with user credentials")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws NoUserFoundException {
        SecurityContextHolder.clearContext();
        User user = null;
        try {
            user = userService.getByUsername(loginRequest.getUsername());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.SUCCESSFUL, null));
            return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
        } catch (Exception e) {
            if (user != null) {
                actionService.add(Action.newAction(user, Action.ActionType.LOGIN, Action.ActionStatus.FAILED, "Wrong password entered."));
                return new ResponseEntity<>("Wrong password entered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong username entered.", HttpStatus.OK);
            }
        }
    }

    @ApiOperation("Logout from the system with user credentials")
    @GetMapping("/logout")
    public ResponseEntity<?> login() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("You successfully logged out", HttpStatus.OK);
    }
}
