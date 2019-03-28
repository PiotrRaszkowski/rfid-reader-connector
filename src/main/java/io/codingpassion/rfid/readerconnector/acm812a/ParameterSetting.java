package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

@Builder
public class ParameterSetting {

    @Getter
    private String name;

    private String commandPattern;

    private int parameterValue;

    public String getCommandCode() {
        String commandCodeHead = MessageFormat.format(commandPattern, StringUtils.leftPad(Integer.toHexString(parameterValue), 2, '0'));
        return (commandCodeHead + ChecksumCalculator.calculate(commandCodeHead)).toUpperCase();
    }
}
