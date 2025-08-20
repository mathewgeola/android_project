package com.example.studyapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

public class OrdinaryService extends Service {
    private static final String TAG = "OrdinaryService";

    public OrdinaryService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//                onStartCommand 返回值

//                START_STICKY_COMPATIBILITY
//                会重新拉起服务，但不回重新调用 onStartCommand

//                START_STICKY
//                会重新拉起服务，但 intent 为 NULL

//                START_NOT_STICKY
//                不会重新拉起服务

//                START_REDELIVER_INTENT
//                会重新拉起服务，但 intent 为原来的值

        Log.d(TAG, "onStartCommand: Looper.myLooper().getThread().getName() = " + Looper.myLooper().getThread().getName());
        new Thread(() -> Log.d(TAG, "run: Looper.myLooper() = " + Looper.myLooper())).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}