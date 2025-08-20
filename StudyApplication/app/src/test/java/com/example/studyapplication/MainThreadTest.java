package com.example.studyapplication;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class MainThreadTest {
    @Test
    public void test() {
        MainThread mainThread = new MainThread();
        mainThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

//        boolean interrupted = Thread.currentThread().isInterrupted();
//        System.out.println("interrupted = " + interrupted);

        mainThread.getLooper().setTask(() -> System.out.println("a runnable task 1"));
        mainThread.getLooper().setTask(() -> System.out.println("a runnable task 2"));

        mainThread.getLooper().quit();

        try {
            mainThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": task 1");
            stringThreadLocal.set("task 1");
            atomicInteger.set(1);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
            System.out.println(Thread.currentThread().getName() + ": " + atomicInteger.get());
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": task 2");
            stringThreadLocal.set("task 2");
            atomicInteger.set(2);
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
            System.out.println(Thread.currentThread().getName() + ": " + atomicInteger.get());
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
