package com.mediscreen.microservice_PractitionerNote.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the patient note requested from database doesn't exist
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PractitionerNoteNotFoundException extends RuntimeException {
    public PractitionerNoteNotFoundException(String message) {
        super(message);
    }
}
