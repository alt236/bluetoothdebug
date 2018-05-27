package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import uk.co.alt236.bluetoothdebug.BuildConfig;
import uk.co.alt236.bluetoothdebug.log.LogControl;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DebugScanCallback extends ScanCallback implements LogControl {
    private static final String DEFAULT_TAG = BuildConfig.DEFAULT_SCAN_LOG_TAG;
    private final ScanCallback callback;
    private final ScanCallbackLogger logger;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public DebugScanCallback() {
        this(DEFAULT_TAG,
                new ScanCallback() {
                });
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public DebugScanCallback(@NonNull final ScanCallback callback) {
        this(DEFAULT_TAG, callback);
    }


    @SuppressWarnings({"unused", "WeakerAccess"})
    public DebugScanCallback(@NonNull String logTag,
                             @NonNull final ScanCallback callback) {
        this(logTag,
                new ScanCallbackLogger(DEFAULT_TAG),
                callback);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    @VisibleForTesting
    DebugScanCallback(
            @NonNull final String logTag,
            @NonNull final ScanCallbackLogger logger,
            @NonNull final ScanCallback callback) {
        this.callback = callback;
        this.logger = logger;

        new Validator(logTag).validate(
                callback.getClass(),
                this.getClass());
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        logger.onScanResult(callbackType, result);
        callback.onScanResult(callbackType, result);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        logger.onBatchScanResults(results);
        callback.onBatchScanResults(results);
    }

    @Override
    public void onScanFailed(int errorCode) {
        logger.onScanFailed(errorCode);
        callback.onScanFailed(errorCode);
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
