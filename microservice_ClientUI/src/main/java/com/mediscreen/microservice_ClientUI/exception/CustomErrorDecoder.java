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
        if (response.status() > 400 && response.status() <=499) {
            return new PersonalRecordNotFoundException("Invalid request");
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
