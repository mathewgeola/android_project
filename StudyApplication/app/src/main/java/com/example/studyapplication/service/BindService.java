package com.example.studyapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {
    private static final String TAG = "BindDemoService";
    private final IBinder binder = new LocalBinder();

    public static class LocalBinder extends Binder {
        private static final String TAG = "DemoBind";

        public void startWork() {
            Log.d(TAG, "startWork: ");
        }

        public int getWorkProcess() {
            Log.d(TAG, "getWorkProcess: ");
            return 50;
        }
    }

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}