package com.example.studyapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studyapplication.reflection.Box;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionDemoActivity extends AppCompatActivity {
    private static final String TAG = "ReflectionDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reflection_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            getClassDemo();

            classDemo();

            constructorDemo();

            fieldDemo();

            methodDemo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getClassDemo() throws Exception {
        // 1. Box.class
        Class<Box> boxClass1 = Box.class;
        Log.d(TAG, "boxClass1: " + boxClass1);

        // 2. Class.forName("com.example.studyapplication.reflection.Box")
        Class<?> boxClass2 = Class.forName("com.example.studyapplication.reflection.Box");
        Log.d(TAG, "boxClass2: " + boxClass2);

        // 3. getClassLoader().loadClass("com.example.studyapplication.reflection.Box")
        Class<?> boxClass3 = getClassLoader().loadClass("com.example.studyapplication.reflection.Box");
        Log.d(TAG, "boxClass3: " + boxClass3);

        // 4. (new Box()).getClass()
        Box box = new Box();
        Class<? extends Box> boxClass4 = box.getClass();
        Log.d(TAG, "boxClass4: " + boxClass4);

        Log.d(TAG, "box: " + box);
        Log.d(TAG, "getClassDemo: " + boxClass1.equals(boxClass2) + "-" + boxClass2.equals(boxClass3) + "-" + boxClass3.equals(boxClass4));
    }

    private void classDemo() throws Exception {
        Class<Box> boxClass = Box.class;

        Class<?>[] classes = boxClass.getClasses();
        for (Class<?> aClass : classes) {
            Log.d(TAG, "aClass: " + aClass);
        }

        Class<?>[] declaredClasses = boxClass.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            Log.d(TAG, "declaredClass: " + declaredClass);
        }

        Log.d(TAG, "boxClass.getName(): " + boxClass.getName());
        Log.d(TAG, "boxClass.getSimpleName(): " + boxClass.getSimpleName());
    }

    private void constructorDemo() throws Exception {
        Class<Box> boxClass = Box.class;
        Constructor<?>[] boxClassConstructors = boxClass.getConstructors();
        for (Constructor<?> boxClassConstructor : boxClassConstructors) {
            Log.d(TAG, "boxClassConstructor: " + boxClassConstructor);
        }
    }

    private void fieldDemo() throws Exception {
        Box box = new Box();
        Class<Box> boxClass = Box.class;

        Field publicStringField = boxClass.getField("publicStringField");
        Log.d(TAG, "publicStringField: " + publicStringField);

        Field publicStaticStringField = boxClass.getField("publicStaticStringField");
        Log.d(TAG, "publicStaticStringField: " + publicStaticStringField);

        box.setPublicStringField("box.setPublicStringField(xxx)");
        Log.d(TAG, "box.getPublicStringField(): " + box.getPublicStringField());
        Log.d(TAG, "publicStringField.get(box): " + publicStringField.get(box));

        publicStringField.set(box, "publicStringField.set(box, \"xxx\");");
        Log.d(TAG, "box.getPublicStringField(): " + box.getPublicStringField());
        Log.d(TAG, "publicStringField.get(box): " + publicStringField.get(box));

        Field[] declaredFields = boxClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Log.d(TAG, declaredField.getName() + ": " + declaredField.get(box));
        }
    }

    private void methodDemo() throws Exception {
        Box box = new Box();
        Class<Box> boxClass = Box.class;

        Method publicMethod1 = boxClass.getMethod("publicMethod", Object[].class);
        Log.d(TAG, "publicMethod: " + publicMethod1);

        Method publicMethod2 = boxClass.getMethod("publicMethod", int.class, Integer.class, byte[].class);
        Log.d(TAG, "publicMethod: " + publicMethod2);

        box.publicMethod(1);
        box.publicMethod(1, 1, new byte[]{1});
        box.publicMethod(new Object[]{1, 1, new byte[]{1}});

        publicMethod1.invoke(box, new Object[]{new Object[]{1}});
        publicMethod2.invoke(box, 1, 1, new byte[]{1});
        publicMethod1.invoke(box, new Object[]{new Object[]{1, 1, new byte[]{1}}});
    }
}