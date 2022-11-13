package com.raf.reservationservice.exception;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException exception) {
        //Create error details object based on exception fields
//        ErrorDetails errorDetails = new ErrorDetails(exception.getErrorCode(), exception.getMessage(), Instant.now());
        //Return error details and map http status from exception
        Message message = new Message(exception.getMessage());
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(message), exception.getHttpStatus());
    }
}