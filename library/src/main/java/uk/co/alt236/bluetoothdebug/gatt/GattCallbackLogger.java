package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.Locale;

import uk.co.alt236.bluetoothdebug.byteformat.ByteFormat;
import uk.co.alt236.bluetoothdebug.gatt.mappers.BluetoothConnectionStateMapper;
import uk.co.alt236.bluetoothdebug.gatt.mappers.GattStatusMapper;
import uk.co.alt236.bluetoothdebug.log.LogControl;
import uk.co.alt236.bluetoothdebug.log.LogEngine;
import uk.co.alt236.bluetoothdebug.mappers.BluetoothObjectStringMapper;

class GattCallbackLogger implements LogControl {
    private static final String LOG_TEMPLATE_PREFIX = "%s %s %d/%d: ";

    private static final Locale LOCALE = Locale.US;
    private static final String SPACE = " ";

    private final LogEngine logger;
    private final BluetoothObjectStringMapper btStringMapper;
    private final GattStatusMapper gattStatusMapper;
    private final BluetoothConnectionStateMapper btConnectionStateMapper;
    private boolean loggingEnabled;

    GattCallbackLogger(@NonNull final String logTag,
                       @NonNull final ByteFormat byteFormat) {
        this(new LogEngine(logTag), byteFormat);
    }

    @VisibleForTesting
    GattCallbackLogger(@NonNull final LogEngine logEngine,
                       @NonNull final ByteFormat byteFormat) {
        this.logger = logEngine;
        this.gattStatusMapper = new GattStatusMapper();
        this.btConnectionStateMapper = new BluetoothConnectionStateMapper();
        this.btStringMapper = new BluetoothObjectStringMapper(byteFormat);
    }

    public void onPhyUpdate(BluetoothGatt gatt,
                            int txPhy,
                            int rxPhy,
                            int status) {

        if (loggingEnabled) {
            final String methodName = "onPhyUpdate()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);
            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "TX: %d RX: %d",
                    SPACE, methodName, index, max, txPhy, rxPhy));
        }
    }

    public void onPhyRead(BluetoothGatt gatt,
                          int txPhy,
                          int rxPhy,
                          int status) {
        if (loggingEnabled) {
            final String methodName = "onPhyRead()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "TX: %d RX: %d",
                    SPACE, methodName, index, max, txPhy, rxPhy));
        }
    }

    public void onConnectionStateChange(BluetoothGatt gatt,
                                        int status,
                                        int newState) {

        if (loggingEnabled) {
            final String methodName = "onConnectionStateChange()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "newState: %s",
                    SPACE, methodName, index, max,
                    btConnectionStateMapper.mapToString(newState)));
        }
    }

    public void onServicesDiscovered(BluetoothGatt gatt,
                                     int status) {

        if (loggingEnabled) {
            final String methodName = "onServicesDiscovered()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Services: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.getServices(gatt)));
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt,
                                     BluetoothGattCharacteristic characteristic,
                                     int status) {

        if (loggingEnabled) {
            final String methodName = "onCharacteristicRead()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Characteristic: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.asString(characteristic)));
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt,
                                      BluetoothGattCharacteristic characteristic,
                                      int status) {
        if (loggingEnabled) {
            final String methodName = "onCharacteristicWrite()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Characteristic: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.asString(characteristic)));
        }
    }

    public void onCharacteristicChanged(BluetoothGatt gatt,
                                        BluetoothGattCharacteristic characteristic) {
        if (loggingEnabled) {
            final String methodName = "onCharacteristicChanged()";
            int index = 1;
            int max = 2;

            logDeviceInfo(gatt, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Characteristic: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.asString(characteristic)));
        }
    }

    public void onDescriptorRead(BluetoothGatt gatt,
                                 BluetoothGattDescriptor descriptor,
                                 int status) {
        if (loggingEnabled) {
            final String methodName = "onDescriptorRead()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Descriptor: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.asString(descriptor)));
        }
    }

    public void onDescriptorWrite(BluetoothGatt gatt,
                                  BluetoothGattDescriptor descriptor,
                                  int status) {
        if (loggingEnabled) {
            final String methodName = "onDescriptorWrite()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Descriptor: %s",
                    SPACE, methodName, index, max,
                    btStringMapper.asString(descriptor)));
        }
    }


    public void onReliableWriteCompleted(BluetoothGatt gatt,
                                         int status) {
        if (loggingEnabled) {
            final String methodName = "onReliableWriteCompleted()";
            int index = 1;
            int max = 2;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index, max);
        }
    }

    public void onReadRemoteRssi(BluetoothGatt gatt,
                                 int rssi,
                                 int status) {

        if (loggingEnabled) {
            final String methodName = "onReadRemoteRssi()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "RSSI: %d",
                    SPACE, methodName, index, max, rssi));
        }
    }

    public void onMtuChanged(BluetoothGatt gatt,
                             int mtu,
                             int status) {

        if (loggingEnabled) {
            final String methodName = "onMtuChanged()";
            int index = 1;
            int max = 3;

            logDeviceInfo(gatt, methodName, index++, max);
            logGattStatus(status, methodName, index++, max);

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "MTU: %d",
                    SPACE, methodName, index, max, mtu));
        }
    }

    private void logDeviceInfo(BluetoothGatt gatt, String methodName, int position, int max) {
        logger.log(String.format(LOCALE,
                LOG_TEMPLATE_PREFIX + " Device: %s",
                SPACE, methodName, position, max,
                btStringMapper.asString(gatt)));
    }

    private void logGattStatus(int status, String methodName, int position, int max) {
        logger.log(String.format(LOCALE,
                LOG_TEMPLATE_PREFIX + " GATT status: %s",
                SPACE, methodName, position, max,
                gattStatusMapper.mapToString(status)));
    }

    @Override
    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    @Override
    public void setLoggingEnabled(final boolean enabled) {
        this.loggingEnabled = enabled;
    }
}
