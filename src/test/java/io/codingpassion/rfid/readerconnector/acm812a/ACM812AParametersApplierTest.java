package io.codingpassion.rfid.readerconnector.acm812a;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import io.codingpassion.rfid.readerconnector.reader.BlockingExecutableSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ACM812AParametersApplierTest {

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private SubscriptionDispatcher subscriptionDispatcher;

    @Mock
    private ParametersRegistry parametersRegistry;

    @InjectMocks
    private ACM812AParametersApplier acm812AParametersApplier;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void applyUserCodeGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = -1 is out of range. Should be 0 - 255");

        acm812AParametersApplier.applyUserCode("-1");
    }

    @Test
    public void applyUserCodeGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 256 is out of range. Should be 0 - 255");

        acm812AParametersApplier.applyUserCode("256");
    }

    @Test
    public void applyUserCodeGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyUserCode("255");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }

    @Test
    public void applyAntennaPowerGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = -1 is out of range. Should be 0 - 150");

        acm812AParametersApplier.applyAntennaPower("-1");
    }

    @Test
    public void applyAntennaPowerGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 151 is out of range. Should be 0 - 150");

        acm812AParametersApplier.applyAntennaPower("151");
    }

    @Test
    public void applyAntennaPowerGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyAntennaPower("40");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }

    @Test
    public void applyReadingTimeIntervalGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 9 is out of range. Should be 10 - 100");

        acm812AParametersApplier.applyReadingTimeInterval("9");
    }

    @Test
    public void applyReadingTimeIntervalGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 102 is out of range. Should be 10 - 100");

        acm812AParametersApplier.applyReadingTimeInterval("102");
    }

    @Test
    public void applyReadingTimeIntervalGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyReadingTimeInterval("10");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }

    @Test
    public void applyAdjacentDiscriminationGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 0 is out of range. Should be 1 - 2");

        acm812AParametersApplier.applyAdjacentDiscrimination("0");
    }

    @Test
    public void applyAdjacentDiscriminationGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 3 is out of range. Should be 1 - 2");

        acm812AParametersApplier.applyAdjacentDiscrimination("3");
    }

    @Test
    public void applyAdjacentDiscriminationGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyAdjacentDiscrimination("1");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }

    @Test
    public void applyAdjacentDiscriminationTimeGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = -1 is out of range. Should be 0 - 255");

        acm812AParametersApplier.applyAdjacentDiscriminationTime("-1");
    }

    @Test
    public void applyAdjacentDiscriminationTimeGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 257 is out of range. Should be 0 - 255");

        acm812AParametersApplier.applyAdjacentDiscriminationTime("257");
    }

    @Test
    public void applyAdjacentDiscriminationTimeGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyAdjacentDiscriminationTime("2");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }

    @Test
    public void applyTagsIdentificationModeGivenValueLtMin() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = -1 is out of range. Should be 0 - 3");

        acm812AParametersApplier.applyTagsIdentificationMode("-1");
    }

    @Test
    public void applyTagsIdentificationModeGivenValueGtMax() {
        //WHEN
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter value = 4 is out of range. Should be 0 - 3");

        acm812AParametersApplier.applyTagsIdentificationMode("4");
    }

    @Test
    public void applyTagsIdentificationModeGivenValueInRange() {
        //WHEN
        acm812AParametersApplier.applyTagsIdentificationMode("2");

        //THEN
        verify(subscriptionDispatcher, only()).addBlockingExecutableSubscriber(any(BlockingExecutableSubscriber.class));
    }
}