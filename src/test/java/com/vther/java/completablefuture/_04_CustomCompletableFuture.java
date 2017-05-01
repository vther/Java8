package com.vther.java.completablefuture;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class _04_CustomCompletableFuture {

    public static void main(String[] args) {
        Future<String> future = invoke(() -> {
            TimeUtils.sleep5Seconds();
            return "I am Finished.";
        });
        future.setCompletable(new Completable<String>() {
            @Override
            public void complete(String s) {
                System.out.println(s);
            }

            @Override
            public void exception(Throwable cause) {
                System.out.println("error");
                cause.printStackTrace();
            }
        });
        System.out.println(".........");
        System.out.println(future.get());
    }

    private static <T> Future<T> invoke(Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);

        Future<T> future = new Future<T>() {

            private Completable<T> completable;

            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }

            @Override
            public Completable<T> getCompletable() {
                return completable;
            }

            @Override
            public void setCompletable(Completable<T> completable) {
                this.completable = completable;
            }
        };
        Thread t = new Thread(() -> {
            try {
                T value = callable.action();
                result.set(value);
                finished.set(true);
                if (future.getCompletable() != null)
                    future.getCompletable().complete(value);
            } catch (Throwable cause) {
                if (future.getCompletable() != null)
                    future.getCompletable().exception(cause);
            }
        });
        t.start();
        return future;
    }


    private interface Future<T> {
        T get();

        boolean isDone();

        Completable<T> getCompletable();

        void setCompletable(Completable<T> completable);
    }

    private interface Callable<T> {
        T action();
    }

    private interface Completable<T> {
        void complete(T t);

        void exception(Throwable cause);
    }
}
