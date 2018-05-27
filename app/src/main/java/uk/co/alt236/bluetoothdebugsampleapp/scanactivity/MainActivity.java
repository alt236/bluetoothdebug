package uk.co.alt236.bluetoothdebugsampleapp.scanactivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;

import java.util.List;

import uk.co.alt236.bluetoothdebug.scanle.DebugScanCallback;
import uk.co.alt236.bluetoothdebugsampleapp.DialogFactory;
import uk.co.alt236.bluetoothdebugsampleapp.R;
import uk.co.alt236.bluetoothdebugsampleapp.bluetooth.BluetoothScanner;
import uk.co.alt236.bluetoothdebugsampleapp.bluetooth.BluetoothValidator;
import uk.co.alt236.bluetoothdebugsampleapp.permissions.CheckCallback;
import uk.co.alt236.bluetoothdebugsampleapp.permissions.CoarseLocationPermissionChecker;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BLUETOOTH_DEBUG";
    private static final int LAYOUT_ID = R.layout.activity_main;
    private static int REQUEST_BLUETOOTH_ENABLE = 1001;

    private BluetoothScanner bluetoothScanner;
    private BluetoothValidator bluetoothValidator;
    private CoarseLocationPermissionChecker permissionChecker;
    private Views views;

    private DebugScanCallback scanCallbackDebug = new DebugScanCallback(
            TAG + "-SCAN",
            new MyScanCallback());

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH_ENABLE) {
            if (resultCode == RESULT_OK) {
                startScan();
            } else if (resultCode == RESULT_CANCELED) {
                showQuitingDialog(
                        "Bluetooth required",
                        "Bluetooth it required for this example to work");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        views = new Views(this);
        permissionChecker = new CoarseLocationPermissionChecker(this);
        bluetoothValidator = new BluetoothValidator(this);
        bluetoothScanner = new BluetoothScanner();

        updateButtonText();
        views.setScanButtonClickListener(v -> {
            if (bluetoothScanner.isScanning()) {
                stopScan();
            } else {
                setupBluetooth();
            }
        });
    }

    @Override
    protected void onPause() {
        stopScan();
        super.onPause();
    }

    private void setupBluetooth() {
        switch (bluetoothValidator.validate()) {
            case NO_BLUETOOTH:
                showQuitingDialog(
                        "Not compatible",
                        "This device does not support Bluetooth");
                break;
            case NO_BLUETOOTH_LE:
                showQuitingDialog(
                        "Not compatible",
                        "This sample app requires a device supporting Bluetooth LE");
                break;
            case OK:
                final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter.isEnabled()) {
                    startScan();
                } else {
                    final Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_BLUETOOTH_ENABLE);
                }
                break;
        }
    }

    private void stopScan() {
        Log.d(TAG, "Stopping scan");
        bluetoothScanner.stopScan(scanCallbackDebug);
        updateButtonText();
    }

    private void startScan() {
        views.clearList();
        permissionChecker.check(new CheckCallback() {
            @Override
            public void onPermissionGranted() {
                Log.d(TAG, "Starting scan");
                Toast.makeText(MainActivity.this, "Starting scan", Toast.LENGTH_SHORT).show();
                scanCallbackDebug.setLoggingEnabled(true);
                bluetoothScanner.startScan(scanCallbackDebug);
                updateButtonText();
            }

            @Override
            public void onPermissionDenied() {
                showQuitingDialog(
                        "Permission needed",
                        "The Coarse Location permission is needed to receive scan results");
            }
        });
    }

    private void updateButtonText() {
        if (bluetoothScanner.isScanning()) {
            views.setScanButtonText(R.string.button_text_stop_scanning);
        } else {
            views.setScanButtonText(R.string.button_text_start_scan);
        }
    }

    private void showQuitingDialog(String title,
                                   String message) {
        DialogFactory.createQuitingDialog(this, title, message).show();
    }


    private class MyScanCallback extends ScanCallback {
        @Override
        public void onBatchScanResults(final List<ScanResult> results) {
            super.onBatchScanResults(results);

            MainActivity.this.runOnUiThread(() -> views.addToList(results));
        }

        @Override
        public void onScanFailed(final int errorCode) {
            super.onScanFailed(errorCode);

            stopScan();
        }

        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
            super.onScanResult(callbackType, result);

            MainActivity.this.runOnUiThread(() -> views.addToList(result));
        }
    }
}
