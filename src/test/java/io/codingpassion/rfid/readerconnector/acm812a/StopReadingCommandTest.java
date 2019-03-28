package io.codingpassion.rfid.readerconnector.acm812a;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StopReadingCommandTest {
    @Mock
    private CommandExecutor commandExecutor;

    @InjectMocks
    @Spy
    private StopReadingCommand stopReadingCommand = new StopReadingCommand(commandExecutor);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getName() {
        //WHEN
        String name = stopReadingCommand.getName();

        //THEN
        assertThat(name).isNotBlank();
    }

    @Test
    public void onExecute() {
        //WHEN
        stopReadingCommand.onExecute();

        //THEN
        verify(commandExecutor).execute("A002FE60");
    }

    @Test
    public void responseArrivedWhenCorrectResponse() {
        //GIVEN
        doNothing().when(stopReadingCommand).dispose();

        //WHEN
        stopReadingCommand.responseArrived("E00488888884");

        //THEN
        verify(stopReadingCommand, times(1)).dispose();
    }

    @Test
    public void responseArrivedWhenUnrecognizedResponse() {
        //WHEN
        stopReadingCommand.responseArrived("Some Unrecognized response...");

        //THEN
        verify(stopReadingCommand, never()).dispose();
    }
}