package com.example.studyapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.studyapplication.R;

public class ForegroundService extends Service {

    public ForegroundService() {
    }

    private static final String TAG = "ForegroundDemoService";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        String title = "例子";
        String content = "这是一个演示的前台服务";

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            String channelId = "channelId";
            NotificationChannel channel = new NotificationChannel(channelId, "channelName", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("一些描述");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            builder = new Notification.Builder(this, channelId);
        } else {
            builder = new Notification.Builder(this);
        }

        Notification notification = builder.setContentTitle(title).setContentText(content).setLargeIcon(getBitmapFromVector(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.ic_launcher_foreground).build();

        startForeground(NOTIFICATION_ID, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    private Bitmap getBitmapFromVector(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        stopForeground(true);
    }
}
