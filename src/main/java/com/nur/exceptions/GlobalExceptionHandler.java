package com.nur.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceNotAvailableException.class)
    public ResponseEntity<String> handleServiceUnavailableException(ServiceNotAvailableException ex){
        return new ResponseEntity<>("Service Unavailable at this moment", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(TransactionNotFoundException ex){
        return new ResponseEntity<>("Transaction Not Found", HttpStatus.NOT_FOUND);
    }

}
