package io.codingpassion.rfid.readerconnector.reader;

public interface Subscriber {

    void onSubscribe(Subscription subscription);

    void responseArrived(String response);

    void dispose();
}
