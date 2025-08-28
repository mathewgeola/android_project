# LSPosedApplication

##### LSPosed 配置

1. LSPosedApplication/app/src/main/AndroidManifest.xml

    ~~~
    <meta-data
        android:name="xposedmodule"
        android:value="true" />
    <meta-data
        android:name="xposeddescription"
        android:value="一个 xposed 模块" />
    <meta-data
        android:name="xposedminversion"
        android:value="82" />
    ~~~

2. LSPosedApplication/settings.gradle.kts

    ~~~
    maven("https://api.xposed.info/")
    ~~~

3. LSPosedApplication/app/build.gradle.kts

    ~~~
    compileOnly("de.robv.android.xposed:api:82")
    ~~~

4. LSPosedApplication/app/src/main/assets/xposed_init

    ~~~
    com.example.lsposedapplication.MainHook
    ~~~

5. com.example.lsposedapplication.MainHook

    ~~~kotlin
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
    ~~~

