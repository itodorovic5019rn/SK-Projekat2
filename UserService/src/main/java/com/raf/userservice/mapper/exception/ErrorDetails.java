package com.raf.userservice.mapper.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * This class represents error response.
 */
public class ErrorDetails {

    @JsonProperty("error_code")
    private ErrorCode errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    private Instant timestamp;

    public ErrorDetails() {

    }

    public ErrorDetails(ErrorCode errorCode, String errorMessage, Instant timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
