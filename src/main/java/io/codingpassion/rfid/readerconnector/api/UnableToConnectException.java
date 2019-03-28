package io.codingpassion.rfid.readerconnector.api;

public class UnableToConnectException extends RuntimeException {

    public UnableToConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
