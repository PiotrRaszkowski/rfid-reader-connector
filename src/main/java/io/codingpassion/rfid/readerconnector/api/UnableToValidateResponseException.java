package io.codingpassion.rfid.readerconnector.api;

public class UnableToValidateResponseException extends RuntimeException {

    public UnableToValidateResponseException(String message) {
        super(message);
    }
}
