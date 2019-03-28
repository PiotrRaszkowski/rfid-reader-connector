package io.codingpassion.rfid.readerconnector.api;

public class UnableToExecuteCommandException extends RuntimeException {

    public UnableToExecuteCommandException(String message) {
        super(message);
    }

    public UnableToExecuteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
