package io.codingpassion.rfid.readerconnector.acm812a;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChecksumCalculatorTest {

    @Test
    public void calcuate1() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A0038201");

        //THEN
        assertEquals("da", checksum);
    }

    @Test
    public void calcuate2() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A0038204");

        //THEN
        assertEquals("d7", checksum);
    }

    @Test
    public void calcuate3() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A00580010008");

        //THEN
        assertEquals("d2", checksum);
    }

    @Test
    public void calcuate4() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A0068101160100");

        //THEN
        assertEquals("c1", checksum);
    }

    @Test
    public void calcuate5() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A0098104000102011234");

        //THEN
        assertEquals("88", checksum);
    }

    @Test
    public void calcuate6() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A002FC");

        //THEN
        assertEquals("62", checksum);
    }

    @Test
    public void calcuate7() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("E004888888");

        //THEN
        assertEquals("84", checksum);
    }

    @Test
    public void calcuate8() throws DecoderException {
        //WHEN
        String checksum = ChecksumCalculator.calculate("A00560006596");

        //THEN
        assertEquals("00", checksum);
    }
}