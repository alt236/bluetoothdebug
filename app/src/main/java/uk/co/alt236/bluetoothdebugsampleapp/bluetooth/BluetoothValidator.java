package uk.co.alt236.bluetoothdebugsampleapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;

public class BluetoothValidator {

    private final boolean hasBluetoothLe;

    public BluetoothValidator(final Context context) {
        final PackageManager pm = context.getPackageManager();
        hasBluetoothLe = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public ValidationStatus validate() {
        if (!hasBluetoothLe) {
            return ValidationStatus.NO_BLUETOOTH_LE;
        }


        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            return ValidationStatus.NO_BLUETOOTH;
        } else {
            return ValidationStatus.OK;
        }
    }

    public enum ValidationStatus {
        NO_BLUETOOTH,
        NO_BLUETOOTH_LE,
        OK
    }

}
