package com.raf.notificationservice.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException{

    public BadRequestException(String message) {
        super(message, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
}
