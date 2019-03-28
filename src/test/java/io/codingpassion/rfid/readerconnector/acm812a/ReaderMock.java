package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

@Slf4j
public class ReaderMock {

    private ServerSocket serverSocket;

    private Socket clientSocket;

    private DataOutputStream outputStream;

    private DataInputStream inputStream;

    public void start() throws Exception {
        new Thread(new TagReader()).start();
        new Thread(new CommandsOperator()).start();
    }

    public void stop() throws Exception {
        clientSocket.close();
        serverSocket.close();
    }

    public class CommandsOperator implements Runnable {

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(23456);
                clientSocket = serverSocket.accept();

                inputStream = new DataInputStream(clientSocket.getInputStream());
                outputStream = new DataOutputStream(clientSocket.getOutputStream());

                log.info("Client has been connected to the reader!");

                while (true) {
                    log.info("Reader communication is active!");
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = inputStream.read(buffer)) != -1) {
                        String message = Hex.encodeHexString(Arrays.copyOf(buffer, count)).toUpperCase();
                        log.info("Reader received message = {}.", message);

                        if ("A002FE60".equals(message)) {
                            synchronized (outputStream) {
                                outputStream.write(Hex.decodeHex("E00488888884"));
                                outputStream.flush();
                                log.info("Reader sent message = E00488888884.");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class TagReader implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(RandomUtils.nextInt(500, 5000));

                    if (!clientSocket.isConnected()) {
                        continue;
                    }

                    synchronized (outputStream) {
                        log.info("Sending tag read...");
                        outputStream.write(Hex.decodeHex("00FFE3006019D26D1CE9AABBCCDD0152FF"));
                        outputStream.flush();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
