package com.nur.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ServiceNotAvailableException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public ServiceNotAvailableException() {
    }

    public ServiceNotAvailableException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceNotAvailableException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
