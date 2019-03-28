package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class StopReadingCommand extends ACM812AExecutable {

    StopReadingCommand(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public String getName() {
        return "ACM812A Stop Reading";
    }

    @Override
    public void responseArrived(String response) {
        log.info("Validating response = {} for command = {}.", response, getName());
        if ("E00488888884".equals(response)) {
            dispose();
        }
    }

    @Override
    public void onExecute() {
        getCommandExecutor().execute("A002FE60");
    }
}
