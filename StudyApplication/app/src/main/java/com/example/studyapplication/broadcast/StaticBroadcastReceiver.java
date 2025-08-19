package com.example.studyapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class StaticBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "StaticBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "android.intent.action.TIMEZONE_CHANGED")) {
            Toast.makeText(context, "timezone changed", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "onReceive: intent.getAction() = " + intent.getAction());
    }
}