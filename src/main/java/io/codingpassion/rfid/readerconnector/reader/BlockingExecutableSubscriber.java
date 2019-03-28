package io.codingpassion.rfid.readerconnector.reader;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;

import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BlockingExecutableSubscriber implements ExecutableSubscriber {

    private AtomicReference<Subscription> subscription = new AtomicReference<>();

    private CountDownLatch countDownLatch;

    private int timeout = 5;

    private TimeUnit timeoutUnit = TimeUnit.SECONDS;

    public final void execute() {
        countDownLatch = new CountDownLatch(1);

        onExecute();

        try {
            boolean result = countDownLatch.await(timeout, timeoutUnit);

            if (!result) {
                throw new UnableToExecuteCommandException(MessageFormat.format("Unable to execute command = {0}, this command is unable to finish due to timeout, no response from reader.", getName()));
            }
        } catch (InterruptedException e) {
            throw new UnableToExecuteCommandException(MessageFormat.format("Unable to finish execution of command = {0}. Unexpected error occur.", getName()), e);
        }
    }

    public void setExecutionTimeout(int timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeoutUnit = timeUnit;
    }

    protected abstract void onExecute();

    @Override
    public final void onSubscribe(Subscription subscription) {
        this.subscription.set(subscription);
    }

    @Override
    public final void dispose() {
        subscription.get().cancel();

        countDownLatch.countDown();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getName();
    }
}
