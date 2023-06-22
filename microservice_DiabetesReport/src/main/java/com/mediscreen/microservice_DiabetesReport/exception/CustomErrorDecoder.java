package com.mediscreen.microservice_DiabetesReport.exception;

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
        return defaultErrorDecoder.decode(s, response);
    }
}
