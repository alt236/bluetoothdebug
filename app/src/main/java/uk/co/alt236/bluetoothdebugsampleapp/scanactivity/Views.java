package uk.co.alt236.bluetoothdebugsampleapp.scanactivity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

class Views {
    private final ListView listView;
    private final Button button;
    private final ScanResultsAdapter adapter;
    private final List<ScanResult> data;

    Views(final Activity activity) {
        listView = activity.findViewById(android.R.id.list);
        button = activity.findViewById(android.R.id.button1);
        data = new ArrayList<>();

        adapter = new ScanResultsAdapter(activity, data);
        listView.setAdapter(adapter);
    }

    public void setScanButtonClickListener(final View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    public void setScanButtonText(@StringRes final int resId) {
        button.setText(resId);
    }

    public void clearList() {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    public void addToList(final List<ScanResult> results) {
        for (ScanResult result : results) {
            addToList(result);
        }
    }

    /* THIS CAN GET PRETTY SLOW, BUT IT'S A DEMO APP :D */
    public void addToList(final ScanResult result) {
        final BluetoothDevice candidateDevice = result.getDevice();
        if (candidateDevice == null) {
            return;
        }

        BluetoothDevice existingResult;
        boolean isItemUpdate = false;

        // Check if it's an update of an existing item
        for (int i = 0; i < data.size(); i++) {
            existingResult = data.get(i).getDevice();
            if (existingResult.getAddress().equals(candidateDevice.getAddress())) {
                data.set(i, result);
                isItemUpdate = true;
                break;
            }
        }

        // If we didn't update an item, add it to the bottom of the list
        if (!isItemUpdate) {
            data.add(result);
        }

        adapter.notifyDataSetChanged();

    }
}
