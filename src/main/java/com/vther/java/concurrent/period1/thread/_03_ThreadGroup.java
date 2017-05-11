package com.vther.java.concurrent.period1.thread;


import org.junit.Assert;

import java.util.Arrays;
import java.util.stream.Stream;

public class _03_ThreadGroup {

    public static void main(String[] args) {
        _01_testDefaultThreadGroup();
        _02_testParentThreadGroup();
        _03_testThreadGroupList();
        _04_testThreadGroupEnumerate();
        _05_testThreadGroupDaemon();
    }

    private static void _01_testDefaultThreadGroup() {
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

    private static void _02_testParentThreadGroup() {
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread t1 = new Thread(threadGroup, () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is running");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();

        System.out.println(t1.getThreadGroup());//threadGroup
        System.out.println(t1.getThreadGroup().getParent());//main
        System.out.println(t1.getThreadGroup().getParent().getParent());//system
        System.out.println(t1.getThreadGroup().getParent().getParent().getParent());//null
    }

    private static void _03_testThreadGroupList() {
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread t2 = new Thread(threadGroup, () -> {
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                Thread.sleep(200000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        t2.start();

        t2.getThreadGroup().getParent().getParent().list();//system
    }


    private static void _04_testThreadGroupEnumerate() {
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread t2 = new Thread(threadGroup, () -> {
            try {
                Thread.sleep(200000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is running");
        }, "t2");
        t2.start();

        ThreadGroup system = t2.getThreadGroup().getParent().getParent();//取出根ThreadGroup为system

        System.out.println("============ enumerate threads");
        Thread[] threads = new Thread[system.activeCount()];
        system.enumerate(threads, true);// true表示递归，会取出ThreadGroup的子ThreadGroup的子ThreadGroup下的线程
        Stream.of(threads).forEach(System.out::println);

        System.out.println("============ enumerate threadGroups");
        ThreadGroup[] threadGroups = new ThreadGroup[system.activeGroupCount()];
        system.enumerate(threadGroups, true);// true表示递归，会取出ThreadGroup的子ThreadGroup的子ThreadGroup
        Stream.of(threadGroups).forEach(System.out::println);
    }

    private static void _05_testThreadGroupDaemon() {
        // A daemon thread group is automatically destroyed
        // --- when its last thread is stopped or its last thread group is destroyed.

        // 守护线程组，会在它其中最后一个线程摧毁后，自动摧毁
        /*
           threadGroup.setDaemon(false);
            false
            t START
            t END
            false // 守护线程组最后一个线程被销毁后，自己不会销毁，可以显式调用 threadGroup.destroy();来销毁

           threadGroup.setDaemon(true);
            false
            t START
            t END
            true // 守护线程组最后一个线程被销毁后，自己也销毁
         */
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        Thread t = new Thread(threadGroup, () -> {
            System.out.println(Thread.currentThread().getName() + " START");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " END");
        }, "t");
        t.start();

        threadGroup.setDaemon(true);

        System.out.println(threadGroup.isDestroyed());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadGroup.isDestroyed());
    }

}
