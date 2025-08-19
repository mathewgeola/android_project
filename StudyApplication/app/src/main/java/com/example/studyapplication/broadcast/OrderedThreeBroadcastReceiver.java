package com.example.studyapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OrderedThreeBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "OrderedThreeBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onReceive: intent.getAction() = " + intent.getAction());
        String data = getResultData();
        Log.d(TAG, "onReceive: data = " + data);
        setResultData(data + "-" + getClass().getSimpleName());
    }
}
