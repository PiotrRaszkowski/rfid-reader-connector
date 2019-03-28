package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.Builder;
import io.codingpassion.rfid.readerconnector.api.ParametersApplier;

import java.text.MessageFormat;

class ACM812AParametersApplier implements ParametersApplier {

    private CommandExecutor commandExecutor;

    private SubscriptionDispatcher subscriptionDispatcher;

    private ParametersRegistry parametersRegistry;

    ACM812AParametersApplier(CommandExecutor commandExecutor, SubscriptionDispatcher subscriptionDispatcher, ParametersRegistry parametersRegistry) {
        this.commandExecutor = commandExecutor;
        this.subscriptionDispatcher = subscriptionDispatcher;
        this.parametersRegistry = parametersRegistry;
    }

    @Override
    public void applyUserCode(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(0)
                .maxValue(255)
                .commandPattern("A005600064{0}")
                .build());

        parametersRegistry.setUserCode(parameterValue);
    }

    private int applyCommand(ApplyCommandMetadata applyCommandMetadata) {
        int commandValue = parseValue(applyCommandMetadata.value, applyCommandMetadata.minValue, applyCommandMetadata.maxValue);

        ParameterSetting parameterSetting = ParameterSetting.builder()
                .commandPattern(applyCommandMetadata.commandPattern)
                .parameterValue(commandValue)
                .build();

        executeParameterCommand(parameterSetting);

        return commandValue;
    }

    @Builder
    private static class ApplyCommandMetadata {
        private String value;

        private int minValue;

        private int maxValue;

        private String commandPattern;
    }

    private int parseValue(String value, int min, int max) {
        try {
            int parameter= Integer.parseInt(value);

            if (parameter < min || parameter > max) {
                throw new IllegalArgumentException(MessageFormat.format("Parameter value = {0} is out of range. Should be {1} - {2}", value, min, max));
            }

            return parameter;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MessageFormat.format("Unable to convert given value = {0} to integer.", value), e);
        }
    }

    private void executeParameterCommand(ParameterSetting parameterSetting) {
        ParameterCommand parameterCommand = new ParameterCommand(commandExecutor, parameterSetting);

        subscriptionDispatcher.addBlockingExecutableSubscriber(parameterCommand);
    }

    @Override
    public void applyAntennaPower(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(0)
                .maxValue(150)
                .commandPattern("A005600065{0}")
                .build());

        parametersRegistry.setAntennaPower(parameterValue);
    }

    @Override
    public void applyReadingTimeInterval(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(10)
                .maxValue(100)
                .commandPattern("A005600071{0}")
                .build());

        parametersRegistry.setReadingTimeInterval(parameterValue);
    }

    @Override
    public void applyAdjacentDiscrimination(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(1)
                .maxValue(2)
                .commandPattern("A00560007B{0}")
                .build());

        parametersRegistry.setAdjacentDiscrimination(parameterValue);
    }

    @Override
    public void applyAdjacentDiscriminationTime(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(0)
                .maxValue(255)
                .commandPattern("A00560007A{0}")
                .build());

        parametersRegistry.setAdjacentDiscriminationTime(parameterValue);
    }

    @Override
    public void applyTagsIdentificationMode(String value) {
        int parameterValue = applyCommand(ApplyCommandMetadata.builder()
                .value(value)
                .minValue(0)
                .maxValue(3)
                .commandPattern("A005600087{0}")
                .build());

        parametersRegistry.setIdentificationMode(parameterValue);
    }
}
