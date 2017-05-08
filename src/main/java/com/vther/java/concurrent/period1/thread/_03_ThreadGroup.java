package com.vther.java.concurrent.period1.thread;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _03_ThreadGroup {
    @Test
    public void test() {

        //  1, 如果构造Thread的时候不传ThreadGroup，Thread会和拉起它的线程的ThreadGroup一样
        Thread t = new Thread("Thread0001") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        System.out.println("-------------------------------");
        System.out.println(t.getThreadGroup());
        Assert.assertEquals(t.getThreadGroup(), Thread.currentThread().getThreadGroup());
        System.out.println("-------------------------------");

        System.out.println("t.getThreadGroup().activeCount()-->" + t.getThreadGroup().activeCount());
        System.out.println("-------------------------------");
        //  2, 会有3个线程，主线程、Thread0001线程、Monitor Ctrl-Break线程
        Thread[] threads = new Thread[t.getThreadGroup().activeCount()];
        t.getThreadGroup().enumerate(threads);
        Arrays.asList(threads).forEach(thread -> {
            System.out.print(thread);
            System.out.println("--- " + thread.isDaemon());
        });

    }
}
