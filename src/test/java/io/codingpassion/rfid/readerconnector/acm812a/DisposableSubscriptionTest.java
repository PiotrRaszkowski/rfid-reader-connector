package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.reader.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DisposableSubscriptionTest {
    @Mock
    private SubscriptionDispatcher subscriptionDispatcher;

    @Mock
    private Subscriber subscriber;

    @InjectMocks
    private DisposableSubscription disposableSubscription;

    @Test
    public void cancel() {
        //WHEN
        disposableSubscription.cancel();

        //THEN
        verify(subscriptionDispatcher).removeSubscriber(subscriber);
    }
}