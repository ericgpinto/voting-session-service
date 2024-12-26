package com.ericpinto.votingsessionservice.exception;

public class VoteClosedException extends RuntimeException {
    public VoteClosedException(String message) {
        super(message);
    }
}
