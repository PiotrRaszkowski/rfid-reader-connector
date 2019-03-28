package io.codingpassion.rfid.readerconnector.api;

import java.util.Map;

public interface ReaderConnector {

    /**
     * Connects to a reader. If you want to perform any operation with the reader you need to establish the connection.
     *
     * @param connectionParameters a map of connection parameters which are required to establish the connection
     * @throws UnableToConnectException when a connection cannot be established (unchecked exception)
     */
    void connect(Map<String, String> connectionParameters);

    /**
     * This method returns the ParametersApplier object which contains a set of useful methods which you can use to configure your reader.
     *
     * @return {@link ParametersApplier}
     */
    ParametersApplier getParametersApplier();

    /**
     * Stops the reading mode. If a reader is in a continuous read mode this command stops the reading, no added reading listener will be notified.
     *
     * @throws UnableToExecuteCommandException when a command cannot be executed (unchecked exception)
     */
    void stopReading();

    /**
     * Starts the reading mode. This command instructs the reader to start a continuous read mode. All added reading listeners ({@link ReaderConnector#addReadingListener(ReadingListener)}) will be notified in case of tag read.
     *
     * @param tagType a type of a tag to read
     * @throws UnableToExecuteCommandException when a command cannot be executed (unchecked exception)
     * @throws IllegalArgumentException when selected tag type is not supported
     */
    void startReading(TagType tagType);

    /**
     * If a continuous read mode is stopped you can use this method to read tags on demand.
     *
     * @param tagType a type of a tag to read
     * @throws UnableToExecuteCommandException when a command cannot be executed (unchecked exception)
     * @throws IllegalArgumentException when selected tag type is not supported
     */
    void identifyTags(TagType tagType);

    /**
     * Adds a reading listener. In a continuous read mode all added reading listeners will be notified in case of tag read.
     *
     * @param readingListener a reading listener which should be notified in case of tag read
     */
    void addReadingListener(ReadingListener readingListener);

    /**
     * Removes selected reading listener. This listener won't be notified in case of tag read.
     *
     * @param readingListener a reading listener which should be removed
     */
    void removeReadingListener(ReadingListener readingListener);

    /**
     * Restarts the reader, useful when a reader needs a restart in case of applying parameters.
     *
     * @throws UnableToExecuteCommandException when a command cannot be executed (unchecked exception)
     */
    void restart();

    /**
     * Disconnects from the reader.
     */
    void disconnect();
}
