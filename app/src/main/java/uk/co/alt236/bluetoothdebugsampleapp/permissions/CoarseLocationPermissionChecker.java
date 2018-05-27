package uk.co.alt236.bluetoothdebugsampleapp.permissions;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

public class CoarseLocationPermissionChecker {
    private static final String PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final Activity activity;

    public CoarseLocationPermissionChecker(final Activity activity) {
        this.activity = activity;
    }

    public void check(@NonNull final CheckCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Only needed on M+
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,
                    new String[]{PERMISSION}, new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            callback.onPermissionGranted();
                        }

                        @Override
                        public void onDenied(String permission) {
                            callback.onPermissionDenied();
                        }
                    });
        } else {
            callback.onPermissionGranted();
        }
    }

}
