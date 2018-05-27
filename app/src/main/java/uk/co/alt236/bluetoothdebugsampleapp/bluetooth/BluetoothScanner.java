package uk.co.alt236.bluetoothdebugsampleapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;

import java.util.ArrayList;
import java.util.List;

public class BluetoothScanner {

    private final BluetoothLeScanner bluetoothLeScanner;
    private final ScanSettings scanSettings;
    private final List<ScanFilter> scanFilters;
    private boolean scanning;

    public BluetoothScanner() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothLeScanner = adapter == null ? null : adapter.getBluetoothLeScanner();

        scanSettings = new ScanSettings
                .Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        scanFilters = new ArrayList<>();
    }

    public void startScan(final ScanCallback callback) {
        if (bluetoothLeScanner != null) {
            scanning = true;
            bluetoothLeScanner.startScan(scanFilters, scanSettings, callback);
        }
    }

    public void stopScan(final ScanCallback callback) {
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(callback);
            scanning = false;
        }
    }

    public boolean isScanning() {
        return scanning;
    }
}
