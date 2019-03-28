package io.codingpassion.rfid.readerconnector.acm812a;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class ParametersRegistry {

    private int userCode = 0;

    private int readingTimeInterval = 10;

    private int adjacentDiscrimination = 2;

    private int adjacentDiscriminationTime = 1;

    private int antennaPower = 150;

    private int identificationMode = 0;
}
