package io.codingpassion.rfid.readerconnector.api;

public interface ReadingListener {

    void onTagRead(String epc);
}
