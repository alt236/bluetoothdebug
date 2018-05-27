package uk.co.alt236.bluetoothdebug.scanle.mappers;

import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import uk.co.alt236.bluetoothdebug.mappers.BluetoothDeviceToStringMapper;

public class ScanResultToStringMapper {
    private final BluetoothDeviceToStringMapper deviceMapper;

    public ScanResultToStringMapper() {
        deviceMapper = new BluetoothDeviceToStringMapper();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String asString(ScanResult scanResult) {
        return deviceMapper.asString(scanResult.getDevice());
    }
}
