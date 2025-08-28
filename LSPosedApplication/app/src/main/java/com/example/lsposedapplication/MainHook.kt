package com.example.lsposedapplication

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage


class MainHook : IXposedHookLoadPackage {
    companion object {
        private const val TAG = "MainHook"
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        Log.d(TAG, "handleLoadPackage: lpparam: $lpparam")
    }

}