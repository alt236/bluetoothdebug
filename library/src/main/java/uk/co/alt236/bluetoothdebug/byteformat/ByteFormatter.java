package uk.co.alt236.bluetoothdebug.byteformat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

public class ByteFormatter {
    private final AsciiUnprintableToUnicodeSymbolMapper unicodeMapper;

    public ByteFormatter() {
        unicodeMapper = new AsciiUnprintableToUnicodeSymbolMapper();
    }


    @NonNull
    public String formatArray(@NonNull final ByteFormat format,
                              @Nullable final byte[] array) {
        if (array == null) {
            return "null";
        }

        switch (format) {
            case INDIVIDUALLY_AS_ASCII:
                return formatAscii(array);
            case INDIVIDUALLY_AS_ASCII_WITH_UNICODE_SYMBOLS:
                return formatAsciiWithUnicode(array);
            case INDIVIDUALLY_AS_UNSIGNED_BYTE:
                return formatUnsignedByte(array);
            case INDIVIDUALLY_AS_SIGNED_BYTE:
                return formatSignedByte(array);
            default:
                throw new IllegalArgumentException("Unknown format: " + format);
        }
    }

    private String formatSignedByte(final byte[] array) {
        return Arrays.toString(array);
    }

    private String formatUnsignedByte(final byte[] array) {
        final int[] retVal = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            retVal[i] = array[i] & 0xFF;
        }

        return Arrays.toString(retVal);
    }

    private String formatAscii(final byte[] array) {
        final char[] retVal = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            retVal[i] = (char) array[i];
        }
        return Arrays.toString(retVal);
    }

    private String formatAsciiWithUnicode(final byte[] array) {
        final char[] retVal = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            retVal[i] = unicodeMapper.getChar(array[i]);
        }
        return Arrays.toString(retVal);
    }
}
