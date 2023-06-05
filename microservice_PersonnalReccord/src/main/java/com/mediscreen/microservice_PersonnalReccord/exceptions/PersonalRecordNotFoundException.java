package com.mediscreen.microservice_PersonnalReccord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonalRecordNotFoundException extends RuntimeException{
    public PersonalRecordNotFoundException(String message) {
        super(message);
    }
}
