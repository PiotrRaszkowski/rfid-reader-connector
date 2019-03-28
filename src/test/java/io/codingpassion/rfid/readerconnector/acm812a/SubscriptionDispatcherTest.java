package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;
import io.codingpassion.rfid.readerconnector.reader.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import io.codingpassion.rfid.readerconnector.reader.BlockingExecutableSubscriber;

import java.util.concurrent.BlockingQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionDispatcherTest {

    @Mock
    private ReaderResponses readerResponses;

    @Mock
    private BlockingQueue<Subscriber> subscribers;

    @Mock
    private BlockingExecutableSubscriber blockingExecutableSubscriber;

    @Mock
    private Subscriber subscriber;

    @InjectMocks
    private SubscriptionDispatcher subscriptionDispatcher = new SubscriptionDispatcher(readerResponses);

    @Test
    public void addBlockingExecutableSubscriber() {
        //WHEN
        subscriptionDispatcher.addBlockingExecutableSubscriber(blockingExecutableSubscriber);

        //THEN
        verify(blockingExecutableSubscriber, times(1)).onSubscribe(any(DisposableSubscription.class));
        verify(blockingExecutableSubscriber, times(1)).execute();
        verify(subscribers, only()).add(blockingExecutableSubscriber);
    }

    @Test
    public void addBlockingExecutableSubscriberWhenExceptionDuringExecute() {
        //GIVEN
        Mockito.doThrow(UnableToExecuteCommandException.class).when(blockingExecutableSubscriber).execute();

        //WHEN
        subscriptionDispatcher.addBlockingExecutableSubscriber(blockingExecutableSubscriber);

        //THEN
        verify(blockingExecutableSubscriber, times(1)).onSubscribe(any(DisposableSubscription.class));
        verify(blockingExecutableSubscriber, times(1)).execute();
        verify(subscribers, times(1)).add(blockingExecutableSubscriber);
        verify(subscribers, times(1)).remove(blockingExecutableSubscriber);
    }

    @Test
    public void addSubscriber() {
        //WHEN
        subscriptionDispatcher.addSubscriber(subscriber);

        //THEN
        verify(subscriber, times(1)).onSubscribe(any(DisposableSubscription.class));
        verify(subscribers, only()).add(subscriber);
    }

    @Test
    public void removeSubscriber() {
        //WHEN
        subscriptionDispatcher.removeSubscriber(subscriber);

        //THEN
        verify(subscribers, only()).remove(subscriber);
    }
}