package com.vther.java.concurrent.period1.thread;

// static class锁
public class _11_SynchronizedOfStaticClassLock {


    static {
        // 静态代码块最先加载，所以会第一时间抢到StaticClassLock.class的锁
        synchronized (StaticClassLock.class) {
            System.out.println("static " + Thread.currentThread().getName());
            try {
                Thread.sleep(10_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        testStaticClassLock();
    }

    // StaticClassLock的两个函数都使用了锁，所以只有一个能抢到，只有一个方法能执行
    private static void testStaticClassLock() {
        new Thread(StaticClassLock::m1).start();
        new Thread(StaticClassLock::m2).start();
        new Thread(StaticClassLock::m3).start();
    }

    private static class StaticClassLock {
        // 这里的synchronized使用的是StaticClassLock.class的锁
        synchronized static void m1() {
            System.out.println("m1 " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized static void m2() {
            System.out.println("m2 " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static void m3() {
            System.out.println("m3 no synchronized " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
