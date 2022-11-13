package com.raf.notificationservice.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends CustomException{

    public ServerErrorException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
