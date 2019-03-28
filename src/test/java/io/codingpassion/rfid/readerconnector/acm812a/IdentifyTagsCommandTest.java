package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.UnableToValidateResponseException;
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
public class IdentifyTagsCommandTest {

    @Mock
    private CommandExecutor commandExecutor;

    @InjectMocks
    @Spy
    private IdentifyTagsCommand identifyTagsCommand = new IdentifyTagsCommand(commandExecutor);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getName() {
        //WHEN
        String name = identifyTagsCommand.getName();

        //THEN
        assertThat(name).isNotBlank();
    }

    @Test
    public void onExecute() {
        //WHEN
        identifyTagsCommand.onExecute();

        //THEN
        verify(commandExecutor).execute("A0038204D7");
    }

    @Test
    public void responseArrivedWhenCorrectResponse() {
        //GIVEN
        doNothing().when(identifyTagsCommand).dispose();

        //WHEN
        identifyTagsCommand.responseArrived("E00F82123");

        //THEN
        verify(identifyTagsCommand, times(1)).dispose();
    }

    @Test
    public void responseArrivedWhenIncorrectResponse() {
        //WHEN
        expectedException.expect(UnableToValidateResponseException.class);
        expectedException.expectMessage("Unable to validate response = E403820493 of command = ACM812A Identify Tags Command! This response code indicates error! Please check multi-single tags reading option!");

        identifyTagsCommand.responseArrived("E403820493");
    }

    @Test
    public void responseArrivedWhenUnrecognizedResponse() {
        //WHEN
        identifyTagsCommand.responseArrived("Some Unrecognized response...");

        //THEN
        verify(identifyTagsCommand, never()).dispose();
    }
}