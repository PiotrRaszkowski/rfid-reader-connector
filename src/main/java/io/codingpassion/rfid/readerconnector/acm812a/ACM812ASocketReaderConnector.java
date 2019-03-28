package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.ParametersApplier;
import io.codingpassion.rfid.readerconnector.api.ReaderConnector;
import io.codingpassion.rfid.readerconnector.api.ReadingListener;
import io.codingpassion.rfid.readerconnector.api.TagType;
import io.codingpassion.rfid.readerconnector.api.UnableToConnectException;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Map;

@Slf4j
public class ACM812ASocketReaderConnector implements ReaderConnector {

    public static final String HOST_PARAMETER = "HOST";
    public static final String PORT_PARAMETER = "PORT";

    private CommandExecutor commandExecutor;

    private ReaderResponses readerResponses = new ReaderResponses();

    private SubscriptionDispatcher subscriptionDispatcher;

    private ParametersRegistry parametersRegistry = new ParametersRegistry();

    private TagsListener tagsListener = new TagsListener(parametersRegistry);

    @Override
    public void connect(Map<String, String> connectionParameters) {
        try {
            String host = connectionParameters.get(HOST_PARAMETER);
            int port = Integer.parseInt(connectionParameters.get(PORT_PARAMETER));

            final Socket socket = new Socket(host, port);
            socket.setKeepAlive(true);

            log.info("Socket initialized with host = {}, remote port = {}, local port = {}.", host, port, socket.getLocalPort());

            this.commandExecutor = new CommandExecutor(new DataOutputStream(socket.getOutputStream()));

            new Thread(new SocketResponseReader(socket.getInputStream(), readerResponses)).start();

            this.subscriptionDispatcher = new SubscriptionDispatcher(readerResponses);

            new Thread(subscriptionDispatcher).start();

            subscriptionDispatcher.addSubscriber(tagsListener);

            log.info("Connected with ACM812A reader!");
        } catch (Exception e) {
            throw new UnableToConnectException(MessageFormat.format("Unable to connect to ACM812A reader using Socket (TCP/IP) method with host = {0} and port = {1}.", "ddd", "ddd"), e);
        }
    }

    @Override
    public ParametersApplier getParametersApplier() {
        return new ACM812AParametersApplier(commandExecutor, subscriptionDispatcher, parametersRegistry);
    }

    @Override
    public void stopReading() {
        log.info("Stopping reading...");

        tagsListener.deactivateListening();

        subscriptionDispatcher.addBlockingExecutableSubscriber(new StopReadingCommand(commandExecutor));

        log.info("Reading stopped.");
    }

    @Override
    public void startReading(TagType tagType) {
        log.info("Starting reading...");

        this.restart();

        log.info("Reading started.");
    }

    @Override
    public void identifyTags(TagType tagType) {
        switch (tagType) {
            case ACM:
                IdentifyTagsCommand identifyTagsCommand = new IdentifyTagsCommand(commandExecutor);
                subscriptionDispatcher.addBlockingExecutableSubscriber(identifyTagsCommand);
                break;
            default:
                throw new IllegalArgumentException(MessageFormat.format("Unable to identify tags of type = {0}. This tag type is not supported!", tagType));
        }
    }

    @Override
    public void addReadingListener(ReadingListener readingListener) {
        tagsListener.addReadingListener(readingListener);
    }

    @Override
    public void removeReadingListener(ReadingListener readingListener) {
        tagsListener.removeReadingListener(readingListener);
    }

    @Override
    public void restart() {
        log.info("Restarting...");

        subscriptionDispatcher.addBlockingExecutableSubscriber(new RestartCommand(commandExecutor));

        log.info("Restarted.");
    }

    @Override
    public void disconnect() {

    }
}
