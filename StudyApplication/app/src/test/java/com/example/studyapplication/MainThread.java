package com.example.studyapplication;


public class MainThread extends Thread {
    private final Looper looper = new Looper();

    public Looper getLooper() {
        return looper;
    }

    @Override
    public void run() {
        looper.loop();
    }

    public static class Looper {
        private Runnable task;

        private volatile boolean isRunning = true;

        public synchronized void setTask(Runnable task) {
            this.task = task;
        }

        public void quit() {
            isRunning = false;
        }

        public void loop() {
            while (isRunning) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
