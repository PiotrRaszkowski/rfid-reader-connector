package io.codingpassion.rfid.readerconnector.acm812a;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

class ChecksumCalculator {

    static String calculate(String hex) {
        try {
            String checksum = Integer.toHexString(calculate(Hex.decodeHex(hex)));
            return StringUtils.leftPad(checksum, 2, '0');
        } catch (DecoderException e) {
            throw new RuntimeException(MessageFormat.format("Unable to calculate checksum for {0}.", hex), e);
        }
    }

    static int calculate(byte [] data) {
        int checksum = 0;
        for (int i = 0; i < data.length; i++) {
            checksum += (data[i] & 0xFF);
        }
        return (~checksum + 1) & 0xFF;
    }
}
