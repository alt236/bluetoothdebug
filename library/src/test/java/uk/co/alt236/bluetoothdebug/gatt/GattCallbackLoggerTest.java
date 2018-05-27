package uk.co.alt236.bluetoothdebug.gatt;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import uk.co.alt236.bluetoothdebug.byteformat.ByteFormat;
import uk.co.alt236.bluetoothdebug.log.LogEngine;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE,
        sdk = {Build.VERSION_CODES.JELLY_BEAN_MR2, Build.VERSION_CODES.O, Build.VERSION_CODES.LOLLIPOP})
public class GattCallbackLoggerTest {

    private LogEngine logEngine;
    private GattCallbackLogger cut;
    private BluetoothGatt gatt;

    @Before
    public void setUp() {
        gatt = mock(BluetoothGatt.class);
        logEngine = mock(LogEngine.class);
        cut = new GattCallbackLogger(logEngine, ByteFormat.INDIVIDUALLY_AS_SIGNED_BYTE);

        final BluetoothDevice mockDevice = mock(BluetoothDevice.class);
        when(gatt.getDevice()).thenReturn(mockDevice);
        when(mockDevice.getAddress()).thenReturn("1:2:3:4");
        when(mockDevice.getName()).thenReturn("MOCK NAME");
    }


    @Test
    public void onPhyUpdate() {
        cut.setLoggingEnabled(false);
        cut.onPhyUpdate(gatt, 1, 1, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onPhyUpdate(gatt, 1, 1, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onPhyRead() {
        cut.setLoggingEnabled(false);
        cut.onPhyRead(gatt, 1, 1, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onPhyRead(gatt, 1, 1, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onConnectionStateChange() {
        cut.setLoggingEnabled(false);
        cut.onConnectionStateChange(gatt, 1, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onConnectionStateChange(gatt, 1, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onServicesDiscovered() {
        cut.setLoggingEnabled(false);
        cut.onServicesDiscovered(gatt, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onServicesDiscovered(gatt, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onCharacteristicRead() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.setLoggingEnabled(false);
        cut.onCharacteristicRead(gatt, characteristic, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onCharacteristicRead(gatt, characteristic, 1);
        verifyLogEngine().log(Mockito.anyString());

    }

    @Test
    public void onCharacteristicWrite() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.setLoggingEnabled(false);
        cut.onCharacteristicWrite(gatt, characteristic, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onCharacteristicWrite(gatt, characteristic, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onCharacteristicChanged() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);

        cut.setLoggingEnabled(false);
        cut.onCharacteristicChanged(gatt, characteristic);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onCharacteristicChanged(gatt, characteristic);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onDescriptorRead() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);
        final BluetoothGattDescriptor descriptor = mock(BluetoothGattDescriptor.class);
        when(descriptor.getCharacteristic()).thenReturn(characteristic);


        cut.setLoggingEnabled(false);
        cut.onDescriptorRead(gatt, descriptor, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onDescriptorRead(gatt, descriptor, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onDescriptorWrite() {
        final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.randomUUID(), 1, 1);
        final BluetoothGattDescriptor descriptor = mock(BluetoothGattDescriptor.class);
        when(descriptor.getCharacteristic()).thenReturn(characteristic);


        cut.setLoggingEnabled(false);
        cut.onDescriptorWrite(gatt, descriptor, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onDescriptorWrite(gatt, descriptor, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onReliableWriteCompleted() {
        cut.setLoggingEnabled(false);
        cut.onReliableWriteCompleted(gatt, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onReliableWriteCompleted(gatt, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onReadRemoteRssi() {
        cut.setLoggingEnabled(false);
        cut.onReadRemoteRssi(gatt, 1, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onReadRemoteRssi(gatt, 1, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void onMtuChanged() {
        cut.setLoggingEnabled(false);
        cut.onMtuChanged(gatt, 1, 1);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onMtuChanged(gatt, 1, 1);
        verifyLogEngine().log(Mockito.anyString());
    }

    @Test
    public void testGettersAndSetters() {
        assertFalse(cut.isLoggingEnabled());
        cut.setLoggingEnabled(true);
        assertTrue(cut.isLoggingEnabled());
    }

    private void verifyLogNotCalled() {
        Mockito.verify(logEngine, Mockito.never()).log(Mockito.anyString());
        Mockito.verify(logEngine, Mockito.never()).logError(Mockito.anyString());
    }

    private LogEngine verifyLogEngine() {
        return Mockito.verify(logEngine, Mockito.atLeastOnce());
    }
}
