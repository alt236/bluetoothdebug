package uk.co.alt236.bluetoothdebug.byteformat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AsciiUnprintableToUnicodeSymbolMapperTest {

    private AsciiUnprintableToUnicodeSymbolMapper cut;

    @Before
    public void setUp() {
        cut = new AsciiUnprintableToUnicodeSymbolMapper();
    }

    @Test
    public void parsesAllAsciiChars() {
        for (int i = 0; i < 256; i++) {
            cut.getChar((byte) i);
        }
    }

    @Test
    public void returnsCorrectUnicodeSymbol() {
        byte bite = 0;

        // 0-4
        compareChars('␀', cut.getChar(bite++));
        compareChars('␁', cut.getChar(bite++));
        compareChars('␂', cut.getChar(bite++));
        compareChars('␃', cut.getChar(bite++));
        compareChars('␄', cut.getChar(bite++));

        //5-9
        compareChars('␅', cut.getChar(bite++));
        compareChars('␆', cut.getChar(bite++));
        compareChars('␇', cut.getChar(bite++));
        compareChars('␈', cut.getChar(bite++));
        compareChars('␉', cut.getChar(bite++));

        //10-14
        compareChars('␊', cut.getChar(bite++));
        compareChars('␋', cut.getChar(bite++));
        compareChars('␌', cut.getChar(bite++));
        compareChars('␍', cut.getChar(bite++));
        compareChars('␎', cut.getChar(bite++));

        //15-19
        compareChars('␏', cut.getChar(bite++));
        compareChars('␐', cut.getChar(bite++));
        compareChars('␑', cut.getChar(bite++));
        compareChars('␒', cut.getChar(bite++));
        compareChars('␓', cut.getChar(bite++));

        //20-24
        compareChars('␔', cut.getChar(bite++));
        compareChars('␕', cut.getChar(bite++));
        compareChars('␖', cut.getChar(bite++));
        compareChars('␗', cut.getChar(bite++));
        compareChars('␘', cut.getChar(bite++));

        //25-29
        compareChars('␙', cut.getChar(bite++));
        compareChars('␚', cut.getChar(bite++));
        compareChars('␛', cut.getChar(bite++));
        compareChars('␜', cut.getChar(bite++));
        compareChars('␝', cut.getChar(bite++));

        //30-32
        compareChars('␞', cut.getChar(bite++));
        compareChars('␟', cut.getChar(bite));
        //compareChars('␠', cut.getChar(bite++));

        compareChars('␡', cut.getChar((byte) 127));

    }


    private void compareChars(char expected, char actual) {
        if (expected != actual) {
            fail("Expected '" + String.valueOf(expected) + "' for ASCII " + (int) actual);
        }
    }
}