package com.mamotec.energycontrolbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddDeviceToGroupException extends RuntimeException {

    public AddDeviceToGroupException(String message) {
        super(message);
    }

    public AddDeviceToGroupException(String message, Throwable cause) {
        super(message, cause);
    }

}
