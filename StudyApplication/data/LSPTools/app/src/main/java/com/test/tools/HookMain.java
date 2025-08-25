package com.test.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    private static final String TAG = "HookMain";

    void showList(List<String> data) {
        Log.d(TAG, "showList: data = " + data);
    }

    <T> void fun(T t) {

    }


    @SuppressLint("NewApi")
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Log.d(TAG, "handleLoadPackage: " + lpparam.packageName);

        XposedHelpers.findAndHookMethod(HookMain.class, "showList", List.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                List<?> data = (List<?>) param.args[0];
                XposedBridge.log("Hooked showList: data = " + data);
            }
        });

        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        showList(strings);

        XposedHelpers.findAndHookMethod("android.app.Instrumentation", lpparam.classLoader, "prePerformCreate", Activity.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Context context = (Context) param.args[0];

//                Uri uri = Uri.parse("content://com.example.studyapplication.provider/app");
                Uri uri = Uri.parse("content://com.test.tools.provider/app");

                ContentResolver contentResolver = context.getContentResolver();

                Cursor cursor = contentResolver.query(uri, new String[]{"id", "package_name", "so_name"}, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Log.d(TAG, "onCreate: " + cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
                    }
                    cursor.close();
                }

                context.getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        Cursor cursor = contentResolver.query(uri, new String[]{"id", "package_name", "so_name"}, null, null, null);
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                Log.d(TAG, "onChange: " + cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
                            }
                            cursor.close();
                        }
                    }
                });
            }
        });
    }
}
