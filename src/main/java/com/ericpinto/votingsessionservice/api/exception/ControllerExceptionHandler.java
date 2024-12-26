package com.ericpinto.votingsessionservice.api.exception;

import com.ericpinto.votingsessionservice.exception.DuplicateVoteException;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.exception.VoteClosedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleResourceNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomError.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(VoteClosedException.class)
    public ResponseEntity<CustomError> handleClosedVoteException(VoteClosedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomError.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<CustomError> handleDuplicateVoteException(DuplicateVoteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomError.builder()
                        .message(e.getMessage())
                        .build());
    }
}
