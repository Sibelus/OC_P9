package com.mediscreen.microservice_ClientUI.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        logger.info("Error intercepted with response status: {}", response.status());

        // PersonalRecord exception handling
        if (response.status() == 400 && s.contains("PersonalRecordProxy#getPatient")) {
            return new PersonalRecordNotFoundException("This personal record doesn't exist");
        }
        if (response.status() == 400 && s.contains("PersonalRecordProxy#updatePatient")) {
            return new PersonalRecordNotFoundException("You can't update personal record that doesn't exist");
        }
        if (response.status() == 400 && s.contains("PersonalRecordProxy#deletePatient")) {
            return new PersonalRecordNotFoundException("You can't delete personal record that doesn't exist");
        }


        // PractitionerNote exception handling
        if (response.status() == 400 && s.contains("PractitionerNoteProxy#getPatient")) {
            return new PractitionerNoteNotFoundException("This practitioner note doesn't exist");
        }
        if (response.status() == 400 && s.contains("PractitionerNoteProxy#getUpdatePatient")) {
            return new PractitionerNoteNotFoundException("You can't update practitioner note that doesn't exist");
        }
        if (response.status() == 400 && s.contains("PractitionerNoteProxy#getDeletePatient")) {
            return new PractitionerNoteNotFoundException("You can't delete practitioner note doesn't exist");
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
