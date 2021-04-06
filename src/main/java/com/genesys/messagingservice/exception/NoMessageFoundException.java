package com.genesys.messagingservice.exception;

public class NoMessageFoundException extends Exception {
    
	private static final long serialVersionUID = 1L;
	private String message = "Could not find a message with with the given information.";

    @Override
    public String getMessage() {
        return message;
    }
}
