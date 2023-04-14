package com.mamotec.energycontrolbackend.config;

import com.mamotec.energycontrolbackend.domain.error.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("The requested entity could not be found: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "The requested entity could not be found.", ex.getMessage());

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        logger.error("An error occurred: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "An error occurred.", ex.getMessage());

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
