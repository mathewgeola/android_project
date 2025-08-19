package com.example.studyapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class DynamicBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "DynamicBroadcastReceive";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "android.intent.action.TIME_TICK")) {
            Toast.makeText(context, "time tick", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "onReceive: " + intent.getAction());
    }
}
