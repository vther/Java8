package com.vther.java.completablefuture;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class _02_CustomFuture {

    public static void main(String[] args) throws InterruptedException {

        Future<String> future = invoke(() -> {
            TimeUtils.sleep5Seconds();
            return "I am finished.";
        });
        System.out.println("main thread -> I am not blocked");
        //String value = future.get(10, TimeUnit.MICROSECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println("result from thread -> " + future.get());

        String value = block(() -> {
            TimeUtils.sleep5Seconds();
            return "I am finished.";
        });
        System.out.println(value);
    }

    private static <T> T block(Callable<T> callable) {
        return callable.call();
    }

    private static <T> Future<T> invoke(Callable<T> callable) {
        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            result.set(callable.call());
            finished.set(true);
        });
        thread.start();
        return new Future<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }
        };
    }


    private interface Future<T> {
        T get();

        boolean isDone();
    }

    private interface Callable<T> {
        T call();
    }
}
