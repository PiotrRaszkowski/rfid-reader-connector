package io.codingpassion.rfid.readerconnector.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import io.codingpassion.rfid.readerconnector.acm812a.ACM812ASocketReaderConnector;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ReaderConnectorFactoryTest {

    @Test
    public void getReaderConnector() {
        //WHEN
        ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);

        //THEN
        assertThat(readerConnector).isInstanceOf(ACM812ASocketReaderConnector.class);
    }
}