package com.mamotec.energycontrolbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExternalServiceNotAvailableException extends RuntimeException {

    public ExternalServiceNotAvailableException(String message) {
        super(message);
    }

    public ExternalServiceNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
