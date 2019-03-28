package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.OutputStream;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {

    @Mock
    private OutputStream outputStream;

    @InjectMocks
    private CommandExecutor commandExecutor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void executeWhenUnableToDecodeCommandCode() {
        //WHEN
        expectedException.expect(UnableToExecuteCommandException.class);
        expectedException.expectMessage("Unable to execute command with code = dasdas. Please follow the exception to find the reason.");

        commandExecutor.execute("dasdas");
    }

    @Test
    public void execute() throws Exception {
        //WHEN
        commandExecutor.execute("0A");

        //THEN
        verify(outputStream).write(Hex.decodeHex("0A"));
    }
}