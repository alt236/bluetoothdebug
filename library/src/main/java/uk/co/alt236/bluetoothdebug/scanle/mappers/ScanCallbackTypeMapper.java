package uk.co.alt236.bluetoothdebug.scanle.mappers;

import android.bluetooth.le.ScanSettings;

public class ScanCallbackTypeMapper {

    public String mapToString(int state) {
        final String retVal;

        switch (state) {
            case (ScanSettings.CALLBACK_TYPE_ALL_MATCHES):
                retVal = "ALL_MATCHES";
                break;
            case (ScanSettings.CALLBACK_TYPE_FIRST_MATCH):
                retVal = "FIRST_MATCH";
                break;
            case (ScanSettings.CALLBACK_TYPE_MATCH_LOST):
                retVal = "MATCH_LOST";
                break;
            default:
                retVal = "UNKNOWN";
                break;
        }

        return retVal + createSuffix(state);
    }

    private String createSuffix(final int status) {
        return " (" + status + ")";
    }

}
