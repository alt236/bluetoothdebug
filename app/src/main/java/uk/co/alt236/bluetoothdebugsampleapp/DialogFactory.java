package uk.co.alt236.bluetoothdebugsampleapp;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;

public class DialogFactory {
    public static Dialog createQuitingDialog(final Activity activity,
                                             final String title,
                                             final String message) {
        return new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        "Exit Application",
                        (dialog, which) -> activity.finish())
                .create();
    }
}
