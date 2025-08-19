package com.example.studyapplication;

import org.junit.Test;

public class SingletonTest {

    @Test
    public void test() {
        System.out.println(Singleton.getInstance());

        new Thread(() -> {
            System.out.println(Singleton.getInstance());
        }).start();

        new Thread(() -> {
            System.out.println(Singleton.getInstance());
        }).start();
    }
}
