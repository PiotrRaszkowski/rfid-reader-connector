package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
class ParameterCommand extends ACM812AExecutable {

    private ParameterSetting parameterSetting;

    ParameterCommand(CommandExecutor commandExecutor, ParameterSetting parameterSetting) {
        super(commandExecutor);
        this.parameterSetting = parameterSetting;
    }

    @Override
    public String getName() {
        return MessageFormat.format("ACM812A Parameter Setting = {0}.", parameterSetting.getName());
    }

    @Override
    public void onExecute() {
        getCommandExecutor().execute(parameterSetting.getCommandCode());
    }

    @Override
    public void responseArrived(String response) {
        if (response.startsWith("E40360")) {
            String state = response.substring(6, 8);
            String checksum = response.substring(8, 10);

            log.info("Command executed with response = {}, state = {} and checksum = {}.", response, state, checksum);

            dispose();
        }
    }
}
