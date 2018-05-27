package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE,
        sdk = {Build.VERSION_CODES.JELLY_BEAN_MR2, Build.VERSION_CODES.O, Build.VERSION_CODES.LOLLIPOP})
public class DebugBluetoothGattCallbackTest {

    private DebugBluetoothGattCallback cut;
    private BluetoothGattCallback wrappedCallback;
    private GattCallbackLogger logger;
    private BluetoothGatt gatt;

    @Before
    public void setUp() {
        wrappedCallback = mock(BluetoothGattCallback.class);
        logger = mock(GattCallbackLogger.class);
        cut = new DebugBluetoothGattCallback("tag", logger, wrappedCallback);
        gatt = mock(BluetoothGatt.class);

        final BluetoothDevice mockDevice = mock(BluetoothDevice.class);
        when(gatt.getDevice()).thenReturn(mockDevice);
        when(mockDevice.getAddress()).thenReturn("1:2:3:4");
        when(mockDevice.getName()).thenReturn("MOCK NAME");
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.O)
    public void onPhyUpdate() {
        cut.onPhyUpdate(gatt, 1, 1, 1);
        verifyLogger().onPhyUpdate(gatt, 1, 1, 1);
        verifyWrappedClient().onPhyUpdate(gatt, 1, 1, 1);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.O)
    public void onPhyRead() {
        cut.onPhyRead(gatt, 1, 1, 1);
        verifyLogger().onPhyRead(gatt, 1, 1, 1);
        verifyWrappedClient().onPhyRead(gatt, 1, 1, 1);
    }

    @Test
    public void onConnectionStateChange() {
        cut.onConnectionStateChange(gatt, 1, 1);
        verifyLogger().onConnectionStateChange(gatt, 1, 1);
        verifyWrappedClient().onConnectionStateChange(gatt, 1, 1);
    }

    @Test
    public void onServicesDiscovered() {
        cut.onServicesDiscovered(gatt, 1);
        verifyLogger().onServicesDiscovered(gatt, 1);
        verifyWrappedClient().onServicesDiscovered(gatt, 1);
    }

    @Test
    public void onCharacteristicRead() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.onCharacteristicRead(gatt, characteristic, 1);
        verifyLogger().onCharacteristicRead(gatt, characteristic, 1);
        verifyWrappedClient().onCharacteristicRead(gatt, characteristic, 1);

    }

    @Test
    public void onCharacteristicWrite() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.onCharacteristicWrite(gatt, characteristic, 1);
        verifyLogger().onCharacteristicWrite(gatt, characteristic, 1);
        verifyWrappedClient().onCharacteristicWrite(gatt, characteristic, 1);
    }

    @Test
    public void onCharacteristicChanged() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.onCharacteristicChanged(gatt, characteristic);
        verifyLogger().onCharacteristicChanged(gatt, characteristic);
        verifyWrappedClient().onCharacteristicChanged(gatt, characteristic);
    }

    @Test
    public void onDescriptorRead() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);
        final BluetoothGattDescriptor descriptor = mock(BluetoothGattDescriptor.class);
        when(descriptor.getCharacteristic()).thenReturn(characteristic);

        cut.onDescriptorRead(gatt, descriptor, 1);
        verifyLogger().onDescriptorRead(gatt, descriptor, 1);
        verifyWrappedClient().onDescriptorRead(gatt, descriptor, 1);
    }

    @Test
    public void onDescriptorWrite() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);
        final BluetoothGattDescriptor descriptor = mock(BluetoothGattDescriptor.class);
        when(descriptor.getCharacteristic()).thenReturn(characteristic);


        cut.onDescriptorWrite(gatt, descriptor, 1);
        verifyLogger().onDescriptorWrite(gatt, descriptor, 1);
        verifyWrappedClient().onDescriptorWrite(gatt, descriptor, 1);
    }

    @Test
    public void onReliableWriteCompleted() {
        cut.onReliableWriteCompleted(gatt, 1);
        verifyLogger().onReliableWriteCompleted(gatt, 1);
        verifyWrappedClient().onReliableWriteCompleted(gatt, 1);
    }

    @Test
    public void onReadRemoteRssi() {
        cut.onReadRemoteRssi(gatt, 1, 1);
        verifyLogger().onReadRemoteRssi(gatt, 1, 1);
        verifyWrappedClient().onReadRemoteRssi(gatt, 1, 1);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.LOLLIPOP)
    public void onMtuChanged() {
        cut.onMtuChanged(gatt, 1, 1);
        verifyLogger().onMtuChanged(gatt, 1, 1);
        verifyWrappedClient().onMtuChanged(gatt, 1, 1);
    }

    @Test
    public void testGettersAndSetters() {
        cut.setLoggingEnabled(true);
        verify(logger, times(1))
                .setLoggingEnabled(anyBoolean());

        cut.isLoggingEnabled();
        //noinspection ResultOfMethodCallIgnored
        verify(logger, times(1))
                .isLoggingEnabled();
    }

    private BluetoothGattCallback verifyWrappedClient() {
        return verify(wrappedCallback,
                times(1));
    }

    private GattCallbackLogger verifyLogger() {
        return verify(logger,
                times(1));
    }
}
