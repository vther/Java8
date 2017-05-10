package com.vther.java.concurrent.period1.thread;


public class _16_DifferenceBetweenSleepAndWait {


    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        // 1 sleep是Thread的方法，wait是Object的方法
        // 2 sleep不会释放掉monitor，wait会释放掉，并且进入等待获取monitor的队列中
        _02_difference();
        // 3 sleep不一定需要monitor，wait需要
        _03_difference();
        // 4 sleep不需要被唤醒，wait(0)需要
    }

    private static void _02_difference() {
        //new Thread(new SleepThread(), "Sleep - 1").start();
        //new Thread(new SleepThread(), "Sleep - 2").start();

        new Thread(new WaitThread1(), "Wait - 1").start();
        new Thread(new WaitThread2(), "Wait - 2").start();
    }

    private static void _03_difference() {
        try {
            Thread.sleep(1000L);
            synchronized (LOCK) {
                LOCK.wait();// 不加锁调用会抛出java.lang.IllegalMonitorStateException
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class SleepThread implements Runnable {
        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " get Lock");
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " release Lock");
            }
        }
    }

    private static class WaitThread1 implements Runnable {
        @Override
        public void run() {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " get lock");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " release lock");
            }

        }
    }

    private static class WaitThread2 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " get lock.");
                LOCK.notifyAll();
                System.out.println(Thread.currentThread().getName() + " release lock.");
            }
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " get lock.");
                System.out.println(Thread.currentThread().getName() + " release lock.");
            }
        }
    }
}
