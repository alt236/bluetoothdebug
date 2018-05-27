package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE,
        sdk = {Build.VERSION_CODES.O, Build.VERSION_CODES.LOLLIPOP})
public class DebugScanCallbackTest {
    private DebugScanCallback cut;
    private ScanCallback wrappedCallback;
    private ScanCallbackLogger logger;

    @Before
    public void setUp() {
        wrappedCallback = mock(ScanCallback.class);
        logger = mock(ScanCallbackLogger.class);
        cut = new DebugScanCallback("tag", logger, wrappedCallback);
    }

    @Test
    public void onScanResult() {
        final ScanResult scanResult = MockScanResultFactory.createScanResult();

        cut.onScanResult(1, scanResult);
        verifyLogger().onScanResult(1, scanResult);
        verifyWrappedCallback().onScanResult(1, scanResult);
    }

    @Test
    public void onBatchScanResults() {
        final List<ScanResult> resultList = Arrays.asList(
                MockScanResultFactory.createScanResult(),
                MockScanResultFactory.createScanResult());

        cut.onBatchScanResults(resultList);
        verifyLogger().onBatchScanResults(resultList);
        verifyWrappedCallback().onBatchScanResults(resultList);
    }

    @Test
    public void onScanFailed() {
        cut.onScanFailed(123);
        verifyLogger().onScanFailed(123);
        verifyWrappedCallback().onScanFailed(123);
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

    private ScanCallback verifyWrappedCallback() {
        return verify(wrappedCallback,
                times(1));
    }

    private ScanCallbackLogger verifyLogger() {
        return verify(logger,
                times(1));
    }
}