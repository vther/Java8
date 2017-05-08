package com.vther.java.concurrent.period1.thread;


public class _06_InterruptOfThread {
    public static void main(String[] args) {

        // 主线程Main拉起了t线程
        //  1, t.interrupt()在Main线程里面调用，会打断t的执行
        System.out.println("Main Thread start");
        Thread t = new Thread("Thread") {
            @Override
            public void run() {
                try {
                    Thread.sleep(100000L);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        t.start();
        System.out.println();
        System.out.println("before t.interrupt() ---> " + t.isInterrupted());

        t.interrupt();
        System.out.println("after  t.interrupt() ---> " + t.isInterrupted());

        System.out.println();
        System.out.println("Main Thread done");
    }
}
