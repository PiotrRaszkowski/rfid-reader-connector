package io.codingpassion.rfid.readerconnector.acm812a;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ReaderResponses {

    private BlockingQueue<String> responses = new LinkedBlockingDeque<>();

    public void addResponse(String response) {
        responses.add(response);
    }

    public String getResponse() {
        try {
            return responses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
