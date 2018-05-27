package uk.co.alt236.bluetoothdebug.gatt.mappers;

import android.bluetooth.BluetoothProfile;

public class BluetoothConnectionStateMapper {

    public String mapToString(int state) {
        final String retVal;

        switch (state) {
            case (BluetoothProfile.STATE_CONNECTED):
                retVal = "CONNECTED";
                break;
            case (BluetoothProfile.STATE_CONNECTING):
                retVal = "CONNECTING";
                break;
            case (BluetoothProfile.STATE_DISCONNECTED):
                retVal = "DISCONNECTED";
                break;
            case (BluetoothProfile.STATE_DISCONNECTING):
                retVal = "DISCONNECTING";
                break;
            default:
                retVal = "UNKNOWN";
                break;
        }
        return retVal + createSuffix(state);
    }

    private String createSuffix(final int status) {
        return " (" + status + ")";
    }

}
