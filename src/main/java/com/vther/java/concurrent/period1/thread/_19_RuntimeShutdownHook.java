package com.vther.java.concurrent.period1.thread;


public class _19_RuntimeShutdownHook {


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("I will execute when program is killed")));

        while (true) {
            System.out.println("program is running");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // windows下使用 exit 可以执行 hook，使用 stop 无法执行
        // windows下使用 kill 可以执行 hook，使用 kill -9 无法执行
    }


}
