package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.ReadingListener;
import io.codingpassion.rfid.readerconnector.reader.Subscriber;
import io.codingpassion.rfid.readerconnector.reader.Subscription;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
class TagsListener implements Subscriber {

    private Set<ReadingListener> readingListeners = new HashSet<>();

    private AtomicBoolean listeningActive = new AtomicBoolean(true);

    private ParametersRegistry parametersRegistry;

    TagsListener(ParametersRegistry parametersRegistry) {
        this.parametersRegistry = parametersRegistry;
    }

    @Override
    public void onSubscribe(Subscription subscription) {

    }

    @Override
    public void responseArrived(String response) {
        if (listeningActive.get()) {
            if (response.startsWith("00"+ Integer.toHexString(parametersRegistry.getUserCode()).toUpperCase())) {
                String epc = response.substring(4, 28);

                log.info("Tag read with epc = {}.", epc);

                for (ReadingListener readingListener : readingListeners) {
                    readingListener.onTagRead(response);
                }
            }
        }
    }

    @Override
    public void dispose() {

    }

    public void activateListening() {
        listeningActive.set(true);
    }

    public void deactivateListening() {
        listeningActive.set(false);
    }

    public void addReadingListener(ReadingListener readingListener) {
        readingListeners.add(readingListener);
    }

    public void removeReadingListener(ReadingListener readingListener) {
        readingListeners.remove(readingListener);
    }
}
