package io.codingpassion.rfid.readerconnector.api;

public interface ParametersApplier {

    /**
     * Applies the user code parameter. This configuration may be used in responses from the reader as a kind of fixed part of a message. It depends on a reader type.
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyUserCode(String value);

    /**
     * Sets the power of the antenna (transmitting power).
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyAntennaPower(String value);

    /**
     * Sets the reading time interval, in a simple way how much time the device should wait before next data reading.
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyReadingTimeInterval(String value);

    /**
     * Sets the adjacent discrimination. If you choose ID adjacent discrimination, after the reader identifies label ID data each time,
     * compare with the effective label ID data of before, if the same, to dispose tag ID data identified this time,
     * if different, to discriminate as new and effective label ID data.
     * If you do not choose the ID adjacent discrimination, the label ID data the reader identified are all effective data.
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyAdjacentDiscrimination(String value);

    /**
     * Sets the adjacent discrimination time, please follow the description of {@link ParametersApplier#applyAdjacentDiscrimination(String)}.
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyAdjacentDiscriminationTime(String value);

    /**
     * Sets the mode of tags identification, for example single tag identification or multiple tags identification.
     *
     * @param value a value to set
     * @throws ParameterNotSupportedException when a parameter is not supported by selected reader
     * @throws IllegalArgumentException when a value is out of supported range or if a value cannot be converted to normalized form accepted by the reader
     * @throws UnableToExecuteCommandException when a command cannot be executed by the reader
     */
    void applyTagsIdentificationMode(String value);
}
