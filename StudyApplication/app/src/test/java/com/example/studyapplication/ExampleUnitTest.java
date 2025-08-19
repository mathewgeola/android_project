package com.example.studyapplication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    static void runInThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("run in Runnable");
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();

        Thread thread = new Thread(() -> {
            System.out.println("run in Thread");
        });
        thread.start();
    }

    static void runInThreadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        threadFactory.newThread(() -> {
            System.out.println("run in ThreadFactory 1");
        }).start();
        threadFactory.newThread(() -> {
            System.out.println("run in ThreadFactory 2");
        }).start();
    }

    static void runInExecutor() {
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        executorService1.execute(() -> {
            System.out.println("run in Executors.newSingleThreadExecutor 1");
        });
        executorService1.execute(() -> {
            System.out.println("run in Executors.newSingleThreadExecutor 2");
        });
        executorService1.execute(() -> {
            System.out.println("run in Executors.newSingleThreadExecutor 3");
        });
        executorService1.close();

        ExecutorService executorService2 = Executors.newCachedThreadPool();
        executorService2.execute(() -> {
            System.out.println("run in Executors.newCachedThreadPool 1");
        });
        executorService2.execute(() -> {
            System.out.println("run in Executors.newCachedThreadPool 2");
        });
        executorService2.execute(() -> {
            System.out.println("run in Executors.newCachedThreadPool 3");
        });
        executorService2.close();
    }

    static void runInCallable() {
        Callable<String> callable = new Callable<>() {
            @Override
            public String call() throws Exception {
                return "call";
            }
        };
        try (ExecutorService executorService = Executors.newCachedThreadPool();) {
            Future<String> future = executorService.submit(callable);
            try {
                String result = future.get();
                System.out.println("result = " + result);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void test() {
        runInThread();

        runInThreadFactory();

        runInExecutor();

        runInCallable();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}