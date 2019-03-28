package io.codingpassion.rfid.readerconnector.reader;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

@RunWith(MockitoJUnitRunner.class)
public class BlockingExecutableSubscriberTest {

    private CountDownLatch countDownLatch;

    @InjectMocks
    private BlockingExecutableSubscriber blockingExecutableSubscriber = new BlockingExecutableSubscriber() {

        @Override
        protected void onExecute() {
            countDownLatch.countDown();
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void responseArrived(String response) {
        }
    };

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        blockingExecutableSubscriber.onSubscribe(new Subscription() {
            @Override
            public void cancel() {
            }
        });
    }

    @Test
    public void executeWhenDisposesThenFinishes() {
        //WHEN
        countDownLatch = new CountDownLatch(1);

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();

                    blockingExecutableSubscriber.dispose();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        blockingExecutableSubscriber.execute();
    }

    @Test
    public void executeWhenNotDisposes() {
        //WHEN
        countDownLatch = new CountDownLatch(1);

        expectedException.expect(UnableToExecuteCommandException.class);

        blockingExecutableSubscriber.execute();
    }
}