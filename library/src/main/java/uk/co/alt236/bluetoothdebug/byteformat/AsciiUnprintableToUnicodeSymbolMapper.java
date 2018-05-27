package uk.co.alt236.bluetoothdebug.byteformat;

class AsciiUnprintableToUnicodeSymbolMapper {
    private static final char UNICODE_CODE_BLOCK_START = '\u2400';

    public char getChar(final byte input) {
        char chr = (char) input;

        return getChar(chr);
    }

    public char getChar(char chr) {
        if (Character.isISOControl(chr)) {
            return getUnicodeSymbol(chr);
        } else {
            return chr;
        }
    }

    private char getUnicodeSymbol(final char chr) {
        if (chr <= 32) { // space is ASCII 32
            return (char) (UNICODE_CODE_BLOCK_START + chr);
        } else if (chr == 127) {// DEL
            return '\u2421';
        } else {
            throw new IllegalStateException("No mapping for: '" + chr + "' (" + (byte) chr + ")");
        }
    }

}
