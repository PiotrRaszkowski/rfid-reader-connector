package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.reader.Subscriber;
import io.codingpassion.rfid.readerconnector.reader.Subscription;

public class DisposableSubscription implements Subscription {

    private SubscriptionDispatcher subscriptionDispatcher;

    private Subscriber subscriber;

    DisposableSubscription(SubscriptionDispatcher subscriptionDispatcher, Subscriber subscriber) {
        this.subscriptionDispatcher = subscriptionDispatcher;
        this.subscriber = subscriber;
    }

    @Override
    public void cancel() {
        subscriptionDispatcher.removeSubscriber(subscriber);
    }
}
