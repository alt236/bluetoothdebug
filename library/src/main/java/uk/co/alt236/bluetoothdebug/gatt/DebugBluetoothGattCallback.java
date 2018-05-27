package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;

import uk.co.alt236.bluetoothdebug.BuildConfig;
import uk.co.alt236.bluetoothdebug.byteformat.ByteFormat;
import uk.co.alt236.bluetoothdebug.log.LogControl;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DebugBluetoothGattCallback extends BluetoothGattCallback implements LogControl {
    private static final String DEFAULT_TAG = BuildConfig.DEFAULT_GATT_LOG_TAG;
    private static final ByteFormat DEFAULT_BYTE_FORMAT = ByteFormat.INDIVIDUALLY_AS_UNSIGNED_BYTE;
    private final BluetoothGattCallback callback;
    private final GattCallbackLogger logger;

    @SuppressWarnings("unused")
    public DebugBluetoothGattCallback() {
        this(DEFAULT_TAG,
                DEFAULT_BYTE_FORMAT,
                new BluetoothGattCallback() {
                });
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public DebugBluetoothGattCallback(@NonNull ByteFormat byteFormat,
                                      @NonNull BluetoothGattCallback callback) {
        this(DEFAULT_TAG, DEFAULT_BYTE_FORMAT, callback);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public DebugBluetoothGattCallback(@NonNull String logTag,
                                      @NonNull ByteFormat byteFormat,
                                      @NonNull BluetoothGattCallback callback) {
        this(logTag, new GattCallbackLogger(logTag, byteFormat), callback);
    }

    @VisibleForTesting
    @SuppressWarnings({"unused", "WeakerAccess"})
    DebugBluetoothGattCallback(
            @NonNull final String logTag,
            @NonNull final GattCallbackLogger logger,
            @NonNull final BluetoothGattCallback callback) {
        this.logger = logger;
        this.callback = callback;

        new Validator(logTag).validate(
                callback.getClass(),
                this.getClass());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPhyUpdate(BluetoothGatt gatt,
                            int txPhy,
                            int rxPhy,
                            int status) {
        logger.onPhyUpdate(gatt, txPhy, rxPhy, status);
        callback.onPhyUpdate(gatt, txPhy, rxPhy, status);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPhyRead(BluetoothGatt gatt,
                          int txPhy,
                          int rxPhy,
                          int status) {
        logger.onPhyRead(gatt, txPhy, rxPhy, status);
        callback.onPhyRead(gatt, txPhy, rxPhy, status);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt,
                                        int status,
                                        int newState) {
        logger.onConnectionStateChange(gatt, status, newState);
        callback.onConnectionStateChange(gatt, status, newState);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt,
                                     int status) {
        logger.onServicesDiscovered(gatt, status);
        callback.onServicesDiscovered(gatt, status);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt,
                                     BluetoothGattCharacteristic characteristic,
                                     int status) {
        logger.onCharacteristicRead(gatt, characteristic, status);
        callback.onCharacteristicRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt,
                                      BluetoothGattCharacteristic characteristic,
                                      int status) {
        logger.onCharacteristicWrite(gatt, characteristic, status);
        callback.onCharacteristicWrite(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt,
                                        BluetoothGattCharacteristic characteristic) {
        logger.onCharacteristicChanged(gatt, characteristic);
        callback.onCharacteristicChanged(gatt, characteristic);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt,
                                 BluetoothGattDescriptor descriptor,
                                 int status) {
        logger.onDescriptorRead(gatt, descriptor, status);
        callback.onDescriptorRead(gatt, descriptor, status);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt,
                                  BluetoothGattDescriptor descriptor,
                                  int status) {
        logger.onDescriptorWrite(gatt, descriptor, status);
        callback.onDescriptorWrite(gatt, descriptor, status);
    }

    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt,
                                         int status) {
        logger.onReliableWriteCompleted(gatt, status);
        callback.onReliableWriteCompleted(gatt, status);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt,
                                 int rssi,
                                 int status) {
        logger.onReadRemoteRssi(gatt, rssi, status);
        callback.onReadRemoteRssi(gatt, rssi, status);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMtuChanged(BluetoothGatt gatt,
                             int mtu,
                             int status) {
        logger.onMtuChanged(gatt, mtu, status);
        callback.onMtuChanged(gatt, mtu, status);
    }

    @Override
    public boolean isLoggingEnabled() {
        return logger.isLoggingEnabled();
    }

    @Override
    public void setLoggingEnabled(final boolean enabled) {
        logger.setLoggingEnabled(enabled);
    }
}
