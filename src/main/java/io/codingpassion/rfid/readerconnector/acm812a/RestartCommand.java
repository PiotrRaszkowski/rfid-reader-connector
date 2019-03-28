package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class RestartCommand extends ACM812AExecutable {
    RestartCommand(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    protected void onExecute() {
        getCommandExecutor().execute("A00265F9");
    }

    @Override
    public String getName() {
        return "ACM812A Restart";
    }

    @Override
    public void responseArrived(String response) {
        log.info("Validating response = {} for command = {}.", response, getName());
        if ("E4036500B4".equals(response)) {
            dispose();
        }
    }
}
