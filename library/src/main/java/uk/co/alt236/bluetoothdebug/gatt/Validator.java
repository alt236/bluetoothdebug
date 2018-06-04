package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothGattCallback;

import uk.co.alt236.bluetoothdebug.validation.OverrideValidator;

final class Validator extends OverrideValidator<
        BluetoothGattCallback,
        DebugBluetoothGattCallback> {

    Validator(String tag) {
        super(tag);
    }
}
