package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.ReaderConnector;
import io.codingpassion.rfid.readerconnector.api.ReaderConnectorFactory;
import io.codingpassion.rfid.readerconnector.api.ReaderModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Ignore
public class ACM812ASocketReaderConnectorTest {

    private ReaderMock readerMock = new ReaderMock();

    @Before
    public void setUp() throws Exception {
//        readerMock.start();
    }

    @After
    public void tearDown() throws Exception {
//        readerMock.stop();
    }

    @Test
    public void test() throws Exception {
        //GIVEN
        Map<String, String> connectionParameters = new HashMap<>();
//        connectionParameters.put(ACM812ASocketReaderConnector.HOST_PARAMETER, "localhost");
        connectionParameters.put(ACM812ASocketReaderConnector.HOST_PARAMETER, "192.168.1.2");
//        connectionParameters.put(ACM812ASocketReaderConnector.PORT_PARAMETER, "23456");
        connectionParameters.put(ACM812ASocketReaderConnector.PORT_PARAMETER, "12345");

        //WHEN
        ReaderConnector readerConnector = ReaderConnectorFactory.getReaderConnector(ReaderModel.ACM812A_SOCKET);

        readerConnector.connect(connectionParameters);
//        readerConnector.getParametersApplier().applyUserCode("255");
        readerConnector.getParametersApplier().applyReadingTimeInterval("100");
        readerConnector.getParametersApplier().applyAntennaPower("150");
        readerConnector.getParametersApplier().applyAdjacentDiscrimination("1");
        readerConnector.getParametersApplier().applyAdjacentDiscriminationTime("10");

        readerConnector.getParametersApplier().applyTagsIdentificationMode("1");
        readerConnector.restart();

//        readerConnector.stopReading();
//        readerConnector.identifyTags(TagType.ACM);
//        readerConnector.startReading(TagType.ACM);
//        readerConnector.restart();

        while (true) {

        }
    }
}