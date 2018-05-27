package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothGattCallback;

import uk.co.alt236.bluetoothdebug.reflection.OverrideValidator;

final class Validator extends OverrideValidator<
        BluetoothGattCallback,
        DebugBluetoothGattCallback> {

    Validator(String tag) {
        super(tag);
    }
}
