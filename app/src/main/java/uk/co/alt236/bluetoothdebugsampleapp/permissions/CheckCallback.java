package uk.co.alt236.bluetoothdebugsampleapp.permissions;

public interface CheckCallback {
    void onPermissionGranted();

    void onPermissionDenied();
}
