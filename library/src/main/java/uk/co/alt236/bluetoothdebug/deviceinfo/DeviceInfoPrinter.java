package uk.co.alt236.bluetoothdebug.deviceinfo;

import android.os.Build;
import android.util.Log;

import java.util.Locale;

public class DeviceInfoPrinter {
    private static final String TEMPLATE =
            "Device: Manufacturer '%s', " +
                    "Brand '%s', " +
                    "Model '%s', " +
                    "API '%d', " +
                    "BaseBand '%s', " +
                    "Build Type '%s', " +
                    "Build Tags '%s'";

    private final String tag;

    public DeviceInfoPrinter(final String tag) {
        this.tag = tag;
    }

    public void print() {
        Log.i(tag, String.format(Locale.US, TEMPLATE,
                Build.MANUFACTURER,
                Build.BRAND,
                Build.MODEL,
                Build.VERSION.SDK_INT,
                Build.getRadioVersion(),
                Build.TYPE,
                Build.TAGS));
    }
}
