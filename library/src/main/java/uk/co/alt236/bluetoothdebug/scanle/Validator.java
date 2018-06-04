package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanCallback;

import uk.co.alt236.bluetoothdebug.validation.OverrideValidator;

final class Validator extends OverrideValidator<
        ScanCallback,
        DebugScanCallback> {

    Validator(String tag) {
        super(tag);
    }
}
