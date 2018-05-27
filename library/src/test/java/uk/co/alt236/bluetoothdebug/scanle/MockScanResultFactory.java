package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockScanResultFactory {
    public static ScanResult createScanResult() {
        final BluetoothDevice mockDevice = mock(BluetoothDevice.class);
        final ScanResult retVal = mock(ScanResult.class);

        when(retVal.getDevice()).thenReturn(mockDevice);
        when(mockDevice.getAddress()).thenReturn("1:2:3:4");
        when(mockDevice.getName()).thenReturn("MOCK NAME");

        return retVal;
    }
}
