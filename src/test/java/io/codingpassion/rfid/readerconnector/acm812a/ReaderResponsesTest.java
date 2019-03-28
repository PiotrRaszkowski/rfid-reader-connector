package io.codingpassion.rfid.readerconnector.acm812a;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReaderResponsesTest {
    @InjectMocks
    private ReaderResponses readerResponses;

    @Test
    public void getResponseWhenResponseAdded() {
        //GIVEN
        String expectedResponse = "Some Response";
        readerResponses.addResponse(expectedResponse);

        //WHEN
        String response = readerResponses.getResponse();

        //THEN
        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }
}