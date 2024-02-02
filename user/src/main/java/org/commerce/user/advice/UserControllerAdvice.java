package org.commerce.user.advice;

import org.commerce.user.model.ErrorResponse;
import org.commerce.user.util.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleBadRequest(RuntimeException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}
