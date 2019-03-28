package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;
import io.codingpassion.rfid.readerconnector.reader.BlockingExecutableSubscriber;
import io.codingpassion.rfid.readerconnector.reader.Subscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class SubscriptionDispatcher implements Runnable {

    private ReaderResponses readerResponses;

    private BlockingQueue<Subscriber> subscribers = new LinkedBlockingDeque<>();

    public SubscriptionDispatcher(ReaderResponses readerResponses) {
        this.readerResponses = readerResponses;
    }

    @Override
    public void run() {
        log.info("Subscription Dispatcher has been started...");

        while (true) {
            String response = readerResponses.getResponse();

            for (Subscriber subscriber : subscribers) {
                subscriber.responseArrived(response);
            }
        }
    }

    public void addBlockingExecutableSubscriber(BlockingExecutableSubscriber blockingExecutableSubscriber) {
        log.info("Adding new blocking executable subscriber = {}.", blockingExecutableSubscriber);

        subscribers.add(blockingExecutableSubscriber);

        blockingExecutableSubscriber.onSubscribe(new DisposableSubscription(this, blockingExecutableSubscriber));

        execute(blockingExecutableSubscriber);
    }

    private void execute(BlockingExecutableSubscriber blockingExecutableSubscriber) {
        try {
            blockingExecutableSubscriber.execute();
        } catch (UnableToExecuteCommandException e) {
            log.error("Unable to execute command, subscriber will be removed!", e);
            subscribers.remove(blockingExecutableSubscriber);
        }
    }

    public void addSubscriber(Subscriber subscriber) {
        log.info("Adding new subscriber = {}.", subscriber);

        subscriber.onSubscribe(new DisposableSubscription(this, subscriber));

        subscribers.add(subscriber);
    }

    void removeSubscriber(Subscriber subscriber) {
        log.info("Removing subscriber = {}.", subscriber);

        subscribers.remove(subscriber);
    }
}
