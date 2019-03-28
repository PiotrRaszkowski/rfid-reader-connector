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
public class RestartCommandTest {
    @Mock
    private CommandExecutor commandExecutor;

    @InjectMocks
    @Spy
    private RestartCommand restartCommand = new RestartCommand(commandExecutor);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getName() {
        //WHEN
        String name = restartCommand.getName();

        //THEN
        assertThat(name).isNotBlank();
    }

    @Test
    public void onExecute() {
        //WHEN
        restartCommand.onExecute();

        //THEN
        verify(commandExecutor).execute("A00265F9");
    }

    @Test
    public void responseArrivedWhenCorrectResponse() {
        //GIVEN
        doNothing().when(restartCommand).dispose();

        //WHEN
        restartCommand.responseArrived("E4036500B4");

        //THEN
        verify(restartCommand, times(1)).dispose();
    }

    @Test
    public void responseArrivedWhenUnrecognizedResponse() {
        //WHEN
        restartCommand.responseArrived("Some Unrecognized response...");

        //THEN
        verify(restartCommand, never()).dispose();
    }
}