package uk.co.alt236.bluetoothdebug.mappers;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;

import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.bluetoothdebug.byteformat.ByteFormat;
import uk.co.alt236.bluetoothdebug.byteformat.ByteFormatter;

public class BluetoothObjectStringMapper {
    private final BluetoothDeviceToStringMapper bluetoothDeviceMapper;
    private final ByteFormatter byteFormatter;
    private final ByteFormat byteFormat;

    public BluetoothObjectStringMapper(final ByteFormat byteFormat) {
        this.bluetoothDeviceMapper = new BluetoothDeviceToStringMapper();
        this.byteFormatter = new ByteFormatter();
        this.byteFormat = byteFormat;
    }

    public String asString(BluetoothGattCharacteristic characteristic) {
        return characteristic.getUuid() + "=" + formatArray(characteristic.getValue());
    }

    public String asString(BluetoothGattDescriptor descriptor) {
        return descriptor.getUuid() + "=" + formatArray(descriptor.getValue())
                + " for characteristic: " + asString(descriptor.getCharacteristic());
    }

    public String asString(BluetoothGatt gatt) {
        return bluetoothDeviceMapper.asString(gatt.getDevice());
    }

    public String getServices(final BluetoothGatt gatt) {
        final List<String> services = new ArrayList<>();
        for (final BluetoothGattService service : gatt.getServices()) {
            services.add(service.getUuid().toString());
        }
        return String.valueOf(services);
    }


    private String formatArray(final byte[] array) {
        return byteFormatter.formatArray(byteFormat, array);
    }
}
