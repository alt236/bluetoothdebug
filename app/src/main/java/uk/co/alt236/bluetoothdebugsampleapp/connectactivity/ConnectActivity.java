package uk.co.alt236.bluetoothdebugsampleapp.connectactivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import uk.co.alt236.bluetoothdebug.byteformat.ByteFormat;
import uk.co.alt236.bluetoothdebug.gatt.DebugBluetoothGattCallback;
import uk.co.alt236.bluetoothdebugsampleapp.DialogFactory;
import uk.co.alt236.bluetoothdebugsampleapp.R;
import uk.co.alt236.bluetoothdebugsampleapp.bluetooth.BluetoothValidator;

public class ConnectActivity extends AppCompatActivity {
    private static final String EXTRA_DEVICE = ConnectActivity.class.getSimpleName() + ".EXTRA_DEVICE";

    private static final String TAG = "BLUETOOTH_DEBUG";
    private static final int LAYOUT_ID = R.layout.activity_connect;
    private static int REQUEST_BLUETOOTH_ENABLE = 1002;
    private BluetoothGattCallback gattCallback = new MyGattCallback();
    private BluetoothValidator bluetoothValidator;
    private Views views;
    private boolean connected;
    private BluetoothDevice device;
    private BluetoothGatt gatt;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH_ENABLE) {
            if (resultCode == RESULT_OK) {
                connect();
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
        device = getIntent().getParcelableExtra(EXTRA_DEVICE);

        views = new Views(this);
        bluetoothValidator = new BluetoothValidator(this);

        updateButtonText();
        views.showTargetDeviceInfo(device);
        views.setConnectButtonClickListener(v -> {
            if (!isConnected()) {
                setupBluetooth();
            } else {
                disconnect();
            }
        });
    }

    @Override
    protected void onPause() {
        disconnect();
        super.onPause();
    }

    private void connect() {
        DebugBluetoothGattCallback debugGattCallback = new DebugBluetoothGattCallback(
                TAG + "-GATT",
                ByteFormat.INDIVIDUALLY_AS_ASCII_WITH_UNICODE_SYMBOLS,
                gattCallback);
        debugGattCallback.setLoggingEnabled(true);

        updateButtonText();
        views.disableConnectButton();
        views.appendText("Connecting...");
        gatt = device.connectGatt(this, false, debugGattCallback);
    }

    private void disconnect() {
        if (gatt != null) {
            gatt.close();
            gatt = null;
        }
        connected = false;
        updateButtonText();
    }

    private boolean isConnected() {
        return gatt != null && connected;
    }

    private void updateButtonText() {
        if (isConnected()) {
            views.setConnectButtonText(R.string.button_text_disconnect);
        } else {
            views.setConnectButtonText(R.string.button_text_connect);
        }
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
                    connect();
                } else {
                    final Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_BLUETOOTH_ENABLE);
                }
                break;
        }
    }

    private void showQuitingDialog(String title,
                                   String message) {
        DialogFactory.createQuitingDialog(this, title, message).show();
    }

    public static Intent createIntent(@NonNull final Context context,
                                      @NonNull final BluetoothDevice device) {
        final Intent intent = new Intent(context, ConnectActivity.class);
        intent.putExtra(EXTRA_DEVICE, device);

        return intent;
    }

    private class MyGattCallback extends BluetoothGattCallback {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothAdapter.STATE_CONNECTED) {
                connected = true;
                gatt.discoverServices();
            } else if (newState == BluetoothAdapter.STATE_DISCONNECTED) {
                connected = false;
            }

            ConnectActivity.this.runOnUiThread(() -> {
                updateButtonText();
                views.enableConnectButton();
                if (connected) {
                    views.appendText("Connected!");
                } else {
                    views.appendText("Disconnected!");
                }
            });
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Number of services: ")
                    .append(gatt.getServices().size())
                    .append('\n');

            int count = 0;
            for (BluetoothGattService service : gatt.getServices()) {
                count++;
                sb.append(count)
                        .append(" : ")
                        .append(service.getUuid())
                        .append('\n');
            }

            ConnectActivity.this.runOnUiThread(
                    () -> views.appendText(sb.toString()));
        }
    }
}
