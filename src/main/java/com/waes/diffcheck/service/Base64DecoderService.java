package com.waes.diffcheck.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.diffcheck.exception.InvalidJsonFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * This service allows us to decodes base64 encoded Json
 */
@Service
public class Base64DecoderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Base64DecoderService.class);

    private final ObjectMapper objectMapper;

    /**
     * @param objectMapper Jackson's object mapper to validate Json
     */
    public Base64DecoderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Decodes base64 encoded Json
     *
     * @param base64EncodedJson Base64 encoded Json data
     * @return Decoded Json data
     * @throws InvalidJsonFormatException In case of Json is not valid
     */
    public String decodeJson(String base64EncodedJson) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedJson);
        var decodedJson = new String(decodedBytes);
        LOGGER.info("{} is decoded as {}", base64EncodedJson, decodedJson);

        if (isJsonValid(decodedJson)) {
            LOGGER.info("{} is valid json", decodedJson);
            return decodedJson;
        }
        throw new InvalidJsonFormatException("Base64 encoded value: {} is invalid Json format" + base64EncodedJson);
    }

    /**
     * Checks the Json data is valid or not
     *
     * @param json Json data
     * @return True if Json is valid otherwise false
     */
    private boolean isJsonValid(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}