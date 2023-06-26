package com.mediscreen.microservice_DiabetesReport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the personal record requested from database doesn't exist
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonalRecordNotFoundException extends RuntimeException {
    public PersonalRecordNotFoundException(String message) {
        super(message);
    }
}
