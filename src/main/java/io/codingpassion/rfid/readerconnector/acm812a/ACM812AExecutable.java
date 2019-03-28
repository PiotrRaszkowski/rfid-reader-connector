package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.reader.BlockingExecutableSubscriber;

abstract class ACM812AExecutable extends BlockingExecutableSubscriber {

    private CommandExecutor commandExecutor;

    ACM812AExecutable(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
