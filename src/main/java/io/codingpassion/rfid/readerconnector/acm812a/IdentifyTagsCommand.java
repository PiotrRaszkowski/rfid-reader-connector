package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;
import io.codingpassion.rfid.readerconnector.api.UnableToValidateResponseException;

import java.text.MessageFormat;

@Slf4j
class IdentifyTagsCommand extends ACM812AExecutable {

    IdentifyTagsCommand(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public String getName() {
        return "ACM812A Identify Tags Command";
    }

    @Override
    public void onExecute() {
        getCommandExecutor().execute("A0038204D7");
    }

    @Override
    public void responseArrived(String response) {
        log.info("Validating response = {} for command = {}.", response, getName());
        if (response.startsWith("E00F82")) {
            log.info("Response = {} is valid!", response);
            dispose();
        } else if ("E403820493".equals(response)) {
            throw new UnableToValidateResponseException(MessageFormat.format("Unable to validate response = {0} of command = {1}! This response code indicates error! Please check multi-single tags reading option!", response, getName()));
        }
    }
}
