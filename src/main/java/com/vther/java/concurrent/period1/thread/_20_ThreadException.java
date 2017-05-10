package com.vther.java.concurrent.period1.thread;


import java.util.stream.Stream;

public class _20_ThreadException {


    public static void main(String[] args) {
        uncaughtExceptionHandler();
        stackTrace();
    }

    private static void stackTrace() {
        Test1 test1 = new Test1();
        test1.test1();
    }

    private static void uncaughtExceptionHandler() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start");
            System.out.println(100 / 0);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
        });

        t.setUncaughtExceptionHandler((thread, exception) -> {
            System.out.println(thread + " -> " + exception.getMessage());
        });
        t.start();
    }

    private static class Test1 {
        private Test2 test2 = new Test2();

        void test1() {
            test2.test2();
        }
    }

    private static class Test2 {
        void test2() {
            Stream.of(Thread.currentThread().getStackTrace())
                    .filter(stackTraceElement -> !stackTraceElement.isNativeMethod())
                    .forEach(stackTraceElement -> System.out.println(
                            stackTraceElement.getClassName()
                                    + " -> "
                                    + stackTraceElement.getMethodName()
                                    + " -> "
                                    + stackTraceElement.getLineNumber()
                                    + " -> "
                                    + stackTraceElement.toString())
                    );
        }
    }
}
