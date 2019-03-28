package io.codingpassion.rfid.readerconnector.api;

import io.codingpassion.rfid.readerconnector.acm812a.ACM812ASocketReaderConnector;

import java.text.MessageFormat;

public final class ReaderConnectorFactory {

    public static ReaderConnector getReaderConnector(ReaderModel readerModel) {
        switch (readerModel) {
            case ACM812A_SOCKET:
                return new ACM812ASocketReaderConnector();
            default:
                throw new IllegalArgumentException(MessageFormat.format("Reader model = {0} is not yet supported!", readerModel));
        }
    }
}
