package uk.co.alt236.bluetoothdebug.scanle.mappers;

import android.bluetooth.le.ScanCallback;

public class ScanErrorCodeMapper {


    public String mapToString(int errorCode) {
        final String retVal;

        switch (errorCode) {
            case (ScanCallback.SCAN_FAILED_ALREADY_STARTED):
                retVal = "ALREADY_STARTED";
                break;
            case (ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED):
                retVal = "APPLICATION_REGISTRATION_FAILED";
                break;
            case (ScanCallback.SCAN_FAILED_INTERNAL_ERROR):
                retVal = "INTERNAL_ERROR";
                break;
            case (ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED):
                retVal = "FEATURE_UNSUPPORTED";
                break;
//            The following are currently @hidden:
//            case (ScanCallback.SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES):
//            case (ScanCallback.SCAN_FAILED_SCANNING_TOO_FREQUENTLY):
            default:
                retVal = "UNKNOWN";
                break;
        }

        return retVal + createSuffix(errorCode);
    }

    private String createSuffix(final int status) {
        return " (" + status + ")";
    }

}
