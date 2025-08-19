package com.example.studyapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class NormalBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NormalBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "com.example.studyapplication.action.NORMAL")) {
            Toast.makeText(context, "normal", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "onReceive: " + intent.getAction());
    }
}
