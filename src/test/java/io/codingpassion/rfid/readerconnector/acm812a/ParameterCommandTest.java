package io.codingpassion.rfid.readerconnector.acm812a;

import org.junit.Before;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ParameterCommandTest {

    private static final String COMMAND_CODE = "COMMAND_CODE";
    private static final String PARAMETER_NAME = "Parameter Name";

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private ParameterSetting parameterSetting;

    @Spy
    @InjectMocks
    private ParameterCommand parameterCommand = new ParameterCommand(commandExecutor, parameterSetting);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        doReturn(COMMAND_CODE).when(parameterSetting).getCommandCode();

    }

    @Test
    public void getNameWhenNameIsSet() {
        //GIVEN
        doReturn(PARAMETER_NAME).when(parameterSetting).getName();

        //WHEN
        String name = parameterCommand.getName();

        //THEN
        assertThat(name).isEqualTo("ACM812A Parameter Setting = Parameter Name.");
    }

    @Test
    public void getNameWhenNameIsNull() {
        //GIVEN
        doReturn(null).when(parameterSetting).getName();

        //WHEN
        String name = parameterCommand.getName();

        //THEN
        assertThat(name).isEqualTo("ACM812A Parameter Setting = null.");
    }

    @Test
    public void onExecute() {
        //WHEN
        parameterCommand.onExecute();

        //THEN
        verify(commandExecutor).execute(COMMAND_CODE);
    }

    @Test
    public void responseArrivedWhenCorrectResponse() {
        //GIVEN
        doNothing().when(parameterCommand).dispose();

        //WHEN
        parameterCommand.responseArrived("E40360AABBCCDD");

        //THEN
        verify(parameterCommand, times(1)).dispose();
    }

    @Test
    public void responseArrivedWhenUnrecognizedResponse() {
        //WHEN
        parameterCommand.responseArrived("Some Unrecognized response...");

        //THEN
        verify(parameterCommand, never()).dispose();
    }
}