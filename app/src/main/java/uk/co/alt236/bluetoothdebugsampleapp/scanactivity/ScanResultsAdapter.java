package uk.co.alt236.bluetoothdebugsampleapp.scanactivity;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import uk.co.alt236.bluetoothdebugsampleapp.connectactivity.ConnectActivity;

public class ScanResultsAdapter extends ArrayAdapter<ScanResult> {
    private static final int ROW_LAYOUT_ID = android.R.layout.simple_list_item_2;

    ScanResultsAdapter(@NonNull final Context context, final List<ScanResult> data) {
        super(context, ROW_LAYOUT_ID, data);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(ROW_LAYOUT_ID, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ScanResult scanResult = getItem(position);
        //noinspection ConstantConditions
        populateRow(viewHolder, scanResult);
        convertView.setTag(viewHolder);

        convertView.setOnClickListener(v -> {
            final Intent intent = ConnectActivity.createIntent(getContext(), scanResult.getDevice());
            getContext().startActivity(intent);
        });

        return convertView;
    }

    private void populateRow(final ViewHolder viewHolder, final ScanResult scanResult) {
        viewHolder.name.setText(scanResult.getDevice().getName());
        viewHolder.mac.setText(scanResult.getDevice().getAddress());
    }

    private static class ViewHolder {
        private final TextView name;
        private final TextView mac;

        private ViewHolder(final View rootView) {
            name = rootView.findViewById(android.R.id.text1);
            mac = rootView.findViewById(android.R.id.text2);
        }
    }
}
