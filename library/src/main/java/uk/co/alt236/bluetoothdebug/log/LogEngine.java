package uk.co.alt236.bluetoothdebug.log;

import android.util.Log;

public class LogEngine {
    private final String tag;

    public LogEngine(final String tag) {
        this.tag = tag;
    }

    public void log(final String message) {
        Log.i(tag, message);
    }

    public void logError(final String message) {
        Log.e(tag, message);
    }
}
