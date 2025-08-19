package com.example.studyapplication;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadDemoTest {
    private volatile boolean isRunning = true;

    private int count = 0;
    private int otherCount = 0;

    private synchronized void addCount1() {
        count++;
        otherCount++;
    }

    private final Object countObj = new Object();
    private final Object otherCountObj = new Object();

    private void addCount2() {
        synchronized (countObj) {
            count++;
        }
        synchronized (otherCountObj) {
            otherCount++;
        }
    }

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private void addCount3() {
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        count++;
        otherCount++;
        writeLock.unlock();
    }

    private final AtomicInteger cnt = new AtomicInteger();

    private void addCnt() {
        cnt.getAndAdd(1);
    }

    private void shutdown() {
        isRunning = false;
        System.out.println("shutdown");
    }

    private void doRunning() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            shutdown();
        }).start();

        while (isRunning) {
        }
        System.out.println("finish");
    }

    private void doCount() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
//                addCount1();
//                addCount2();
                addCount3();

                addCnt();
            }
        });
        thread1.start();


        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
//                addCount1();
//                addCount2();
                addCount3();

                addCnt();
            }
        });
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("count = " + count);
        System.out.println("otherCount = " + otherCount);
        System.out.println("cnt = " + cnt.get());
    }

    @Test
    public void test() {
        ThreadDemoTest threadDemo = new ThreadDemoTest();
        threadDemo.doRunning();
        threadDemo.doCount();
    }
}
