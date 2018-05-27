package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanCallback;

import uk.co.alt236.bluetoothdebug.reflection.OverrideValidator;

final class Validator extends OverrideValidator<
        ScanCallback,
        DebugScanCallback> {

    Validator(String tag) {
        super(tag);
    }
}
