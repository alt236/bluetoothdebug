package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;

import java.util.List;
import java.util.Locale;

import uk.co.alt236.bluetoothdebug.log.LogControl;
import uk.co.alt236.bluetoothdebug.log.LogEngine;
import uk.co.alt236.bluetoothdebug.scanle.mappers.ScanCallbackTypeMapper;
import uk.co.alt236.bluetoothdebug.scanle.mappers.ScanErrorCodeMapper;
import uk.co.alt236.bluetoothdebug.scanle.mappers.ScanResultToStringMapper;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class ScanCallbackLogger implements LogControl {
    private static final String LOG_TEMPLATE_PREFIX = "%s %s %d/%d: ";

    private static final Locale LOCALE = Locale.US;
    private static final String SPACE = " ";

    private final LogEngine logger;
    private final ScanCallbackTypeMapper scanCallbackTypeMapper;
    private final ScanErrorCodeMapper scanErrorCodeMapper;
    private final ScanResultToStringMapper scanResultToStringMapper;
    private boolean loggingEnabled;

    ScanCallbackLogger(@NonNull final String logTag) {
        this(new LogEngine(logTag));
    }

    @VisibleForTesting
    ScanCallbackLogger(@NonNull final LogEngine logEngine) {
        this.logger = logEngine;
        this.scanCallbackTypeMapper = new ScanCallbackTypeMapper();
        this.scanErrorCodeMapper = new ScanErrorCodeMapper();
        this.scanResultToStringMapper = new ScanResultToStringMapper();
    }

    public void onScanResult(int callbackType,
                             ScanResult result) {
        if (loggingEnabled) {
            final String methodName = "onScanResult()";
            int index = 1;
            int max = 1;

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "Type: %s Result: %s",
                    SPACE, methodName, index, max,
                    scanCallbackTypeMapper.mapToString(callbackType),
                    scanResultToStringMapper.asString(result)));
        }
    }

    public void onBatchScanResults(List<ScanResult> results) {
        if (loggingEnabled) {
            final String methodName = "onBatchScanResults()";
            int index = 1;
            int max = results.size();

            for (ScanResult result : results) {
                logger.log(String.format(LOCALE,
                        LOG_TEMPLATE_PREFIX + "Result: %s",
                        SPACE, methodName, index++, max,
                        scanResultToStringMapper.asString(result)));
            }
        }
    }

    public void onScanFailed(int errorCode) {
        if (loggingEnabled) {
            final String methodName = "onScanFailed()";
            int index = 1;
            int max = 1;

            logger.log(String.format(LOCALE,
                    LOG_TEMPLATE_PREFIX + "ErrorCode: %s",
                    SPACE, methodName, index, max,
                    scanErrorCodeMapper.mapToString(errorCode)));

        }
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    @Override
    public void setLoggingEnabled(final boolean enabled) {
        this.loggingEnabled = enabled;
    }
}
