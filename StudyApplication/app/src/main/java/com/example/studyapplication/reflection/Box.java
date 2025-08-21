package com.example.studyapplication.reflection;

import android.util.Log;

import java.util.Arrays;

public class Box {
    private static final String TAG = "Box";

    public static class InnerPublicStaticClass {
    }

    private static class InnerPrivateStaticClass {
    }

    public static interface InnerPublicStaticInterface {
    }

    private static interface InnerPrivateStaticInterface {
    }

    public Box() {
        Log.d(TAG, "Box()");
    }

    public Box(int a) {
        Log.d(TAG, "Box(int a)");
    }


    public int a;

    public int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }


    @Override
    public String toString() {
        return "Box{" + "a=" + a + ", b=" + b + '}';
    }

    public String publicStringField;

    private String privateStringField;

    public static String publicStaticStringField;

    private static String privateStaticStringField;


    public String getPublicStringField() {
        return publicStringField;
    }

    public void setPublicStringField(String publicStringField) {
        this.publicStringField = publicStringField;
    }

    public String getPrivateStringField() {
        return privateStringField;
    }

    public void setPrivateStringField(String privateStringField) {
        this.privateStringField = privateStringField;
    }

    public static String getPublicStaticStringField() {
        return publicStaticStringField;
    }

    public static void setPublicStaticStringField(String publicStaticStringField) {
        Box.publicStaticStringField = publicStaticStringField;
    }

    public static String getPrivateStaticStringField() {
        return privateStaticStringField;
    }

    public static void setPrivateStaticStringField(String privateStaticStringField) {
        Box.privateStaticStringField = privateStaticStringField;
    }


    private void privateMethod() {
        Log.d(TAG, "privateMethod()");
    }

    public void publicMethod(int a, Integer b, byte[] c) {
        Log.d(TAG, "publicMethod(int a, Integer b, byte[] c) => " + a + ", " + b + ", " + Arrays.toString(c));
    }

    public void publicMethod(Object... args) {
        Log.d(TAG, "publicMethod(Object... args) => " + Arrays.toString(args));
//        if (args.length == 3) {
//            Log.d(TAG, "publicMethod(Object... args): arg[2] = " + args[2]);
//        }
    }
}
