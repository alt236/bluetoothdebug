package uk.co.alt236.bluetoothdebug.gatt.mappers;

import android.bluetooth.BluetoothGatt;

public class GattStatusMapper {

    public String mapToString(int status) {
        final String retVal;

        switch (status) {
            case (BluetoothGatt.GATT_SUCCESS):
                retVal = "SUCCESS";
                break;
            case (BluetoothGatt.GATT_READ_NOT_PERMITTED):
                retVal = "READ_NOT_PERMITTED";
                break;
            case (BluetoothGatt.GATT_WRITE_NOT_PERMITTED):
                retVal = "WRITE_NOT_PERMITTED";
                break;
            case (BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION):
                retVal = "INSUFFICIENT_AUTHENTICATION";
                break;
            case (BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED):
                retVal = "REQUEST_NOT_SUPPORTED";
                break;
            case (BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION):
                retVal = "INSUFFICIENT_ENCRYPTION";
                break;
            case (BluetoothGatt.GATT_INVALID_OFFSET):
                retVal = "INVALID_OFFSET";
                break;
            case (BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH):
                retVal = "INVALID_ATTRIBUTE_LENGTH";
                break;
            case (BluetoothGatt.GATT_CONNECTION_CONGESTED):
                retVal = "CONNECTION_CONGESTED";
                break;
            case (BluetoothGatt.GATT_FAILURE):
                retVal = "FAILURE";
                break;
            default:
                retVal = "UNKNOWN";
                break;
        }

        return retVal + createSuffix(status);
    }

    private String createSuffix(final int status) {
        return " (" + status + ")";
    }
}
