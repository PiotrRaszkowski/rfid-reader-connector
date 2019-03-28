package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
public class SocketResponseReader implements Runnable {

    private DataInputStream dataInputStream;

    private ReaderResponses readerResponses;

    public SocketResponseReader(InputStream inputStream, ReaderResponses readerResponses) {
        this.dataInputStream = new DataInputStream(inputStream);
        this.readerResponses = readerResponses;
    }

    @Override
    public void run() {
        log.info("Socket Reader has been started...");

        byte[] buffer = new byte[1024];
        while (true) {
            try {
                int count;
                while ((count = dataInputStream.read(buffer)) != -1) {
                    String response = Hex.encodeHexString(Arrays.copyOf(buffer, count)).toUpperCase();
                    log.info("Response has been received from the reader = {}.", response);
                    readerResponses.addResponse(response);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
