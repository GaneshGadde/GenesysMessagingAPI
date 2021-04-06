package com.genesys.messagingservice.exception;

public class NoUserFoundException extends Exception {
   
	private static final long serialVersionUID = 1L;
	private String message = "Could not find a user with the given information.";

    @Override
    public String getMessage() {
        return message;
    }
}
