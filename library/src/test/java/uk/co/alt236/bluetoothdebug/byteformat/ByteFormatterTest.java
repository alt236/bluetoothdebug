package uk.co.alt236.bluetoothdebug.byteformat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteFormatterTest {

    private ByteFormatter cut;

    @Before
    public void setUp() {
        cut = new ByteFormatter();
    }

    @Test
    public void returnsNullStringForNullInput() {
        assertEquals("null", cut.formatArray(ByteFormat.INDIVIDUALLY_AS_ASCII, null));
    }

    @Test
    public void testSignedByteFormat() {
        final ByteFormat format = ByteFormat.INDIVIDUALLY_AS_SIGNED_BYTE;
        final byte[] input = new byte[]{-1, 0, 1, 65, 97, 127};
        final String expected = "[-1, 0, 1, 65, 97, 127]";

        assertEquals(expected, cut.formatArray(format, input));
    }

    @Test
    public void testUnsignedByteFormat() {
        final ByteFormat format = ByteFormat.INDIVIDUALLY_AS_UNSIGNED_BYTE;
        final byte[] input = new byte[]{-1, 0, 1, 65, 97, 127};
        final String expected = "[255, 0, 1, 65, 97, 127]";

        assertEquals(expected, cut.formatArray(format, input));
    }

    @Test
    public void testAsciiFormat() {
        final ByteFormat format = ByteFormat.INDIVIDUALLY_AS_ASCII;
        final byte[] input = new byte[]{-1, 0, 1, 65, 97, 127};
        final String expected = "[\uFFFF, \u0000, \u0001, A, a, \u007F]";

        assertEquals(expected, cut.formatArray(format, input));
    }

    @Test
    public void testAsciiWithUnicodeFormat() {
        final ByteFormat format = ByteFormat.INDIVIDUALLY_AS_ASCII_WITH_UNICODE_SYMBOLS;
        final byte[] input = new byte[]{-1, 0, 1, 65, 97, 127};
        final String expected = "[\uFFFF, \u2400, \u2401, A, a, \u2421]";

        assertEquals(expected, cut.formatArray(format, input));
    }
}