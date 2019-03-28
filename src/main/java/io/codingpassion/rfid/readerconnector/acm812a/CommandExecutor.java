package io.codingpassion.rfid.readerconnector.acm812a;

import io.codingpassion.rfid.readerconnector.api.UnableToExecuteCommandException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

@Slf4j
class CommandExecutor  {

    private OutputStream outputStream;

    public CommandExecutor(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void execute(String commandCode) {
        try {
            log.info("Executing command code = {}.", commandCode);
            outputStream.write(Hex.decodeHex(commandCode));
            outputStream.flush();
        } catch (DecoderException | IOException e) {
            throw new UnableToExecuteCommandException(MessageFormat.format("Unable to execute command with code = {0}. Please follow the exception to find the reason.", commandCode), e);
        }
    }
}
