package io.codingpassion.rfid.readerconnector.acm812a;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ParameterSettingTest {

    @Test
    public void getCommandCode() {
        //GIVEN
        ParameterSetting parameterSetting = ParameterSetting.builder().commandPattern("A005600065{0}").parameterValue(150).build();

        //WHEN
        String code = parameterSetting.getCommandCode();

        //THEN
        assertEquals("A0056000659600", code);
    }
}