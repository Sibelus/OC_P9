package com.mediscreen.microservice_DiabetesReport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PractitionerNoteNotFoundException extends RuntimeException {
    public PractitionerNoteNotFoundException(String message) {
        super(message);
    }
}
