package uk.co.alt236.bluetoothdebugsampleapp.connectactivity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class Views {
    private final TextView title;
    private final TextView textView;
    private final Button button;

    Views(final Activity activity) {
        title = activity.findViewById(android.R.id.title);
        textView = activity.findViewById(android.R.id.text1);
        button = activity.findViewById(android.R.id.button1);
    }

    public void setConnectButtonClickListener(final View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    public void setConnectButtonText(@StringRes final int resId) {
        button.setText(resId);
    }

    public void appendText(final String string) {
        textView.append("\n" + string);
    }

    public void showTargetDeviceInfo(final BluetoothDevice device) {
        title.setText(device.getAddress() + " (" + device.getName() + ")");
    }

    public void disableConnectButton() {
        button.setEnabled(false);
    }

    public void enableConnectButton() {
        button.setEnabled(true);
    }
}
