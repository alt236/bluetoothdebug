package uk.co.alt236.bluetoothdebug.mappers;

import android.bluetooth.BluetoothDevice;

public class BluetoothDeviceToStringMapper {
    public String asString(BluetoothDevice device) {
        return device.getAddress() + " (" + device.getName() + ")";
    }
}
