package uk.co.alt236.bluetoothdebug.scanle;

import android.bluetooth.le.ScanResult;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import uk.co.alt236.bluetoothdebug.log.LogEngine;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

public class ScanCallbackLoggerTest {
    private LogEngine logEngine;
    private ScanCallbackLogger cut;

    @Before
    public void setUp() {
        logEngine = mock(LogEngine.class);
        cut = new ScanCallbackLogger(logEngine);
    }

    @Test
    public void onScanResult() {
        final ScanResult scanResult = MockScanResultFactory.createScanResult();

        cut.setLoggingEnabled(false);
        cut.onScanResult(1, scanResult);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onScanResult(1, scanResult);
        verifyLogEngine().log(anyString());
    }

    @Test
    public void onBatchScanResults() {
        final List<ScanResult> resultList = Arrays.asList(
                MockScanResultFactory.createScanResult(),
                MockScanResultFactory.createScanResult());

        cut.setLoggingEnabled(false);
        cut.onBatchScanResults(resultList);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onBatchScanResults(resultList);
        verifyLogEngine().log(anyString());
    }

    @Test
    public void onScanFailed() {
        cut.setLoggingEnabled(false);
        cut.onScanFailed(123);
        verifyLogNotCalled();

        cut.setLoggingEnabled(true);
        cut.onScanFailed(123);
        verifyLogEngine().log(anyString());
    }

    @Test
    public void testGettersAndSetters() {
        assertFalse(cut.isLoggingEnabled());
        cut.setLoggingEnabled(true);
        assertTrue(cut.isLoggingEnabled());
    }

    private void verifyLogNotCalled() {
        verify(logEngine, never()).log(anyString());
        verify(logEngine, never()).logError(anyString());
    }

    private LogEngine verifyLogEngine() {
        return verify(logEngine, atLeastOnce());
    }
}