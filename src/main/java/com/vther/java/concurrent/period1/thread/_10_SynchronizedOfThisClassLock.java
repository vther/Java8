package com.vther.java.concurrent.period1.thread;

// this class锁
public class _10_SynchronizedOfThisClassLock {

    public static void main(String[] args) {
        testThisLock();
        testThisClassLock();
    }


    private static void testThisLock() {
        Runnable runnable = new Runnable3();
        Thread t1 = new Thread(runnable, "一号机器");
        Thread t2 = new Thread(runnable, "二号机器");
        Thread t3 = new Thread(runnable, "三号机器");
        t1.start();
        t2.start();
        t3.start();
    }

    // ThisLock的两个函数都使用了this class锁，所以只有一个能抢到，只有一个方法能执行
    private static void testThisClassLock() {
        ThisClassLock thisLock = new ThisClassLock();
        new Thread(thisLock::m1).start();
        new Thread(thisLock::m2).start();
        new Thread(thisLock::m3).start();
    }

    // 使用这种方式使用的其实是this锁，执行的结果是只有一个机器能抢到锁
    private static class Runnable1 implements Runnable {
        private static final int MAX = 500;
        private int index = 1;

        @Override
        public synchronized void run() {
            while (true) {
                if (index > MAX) {
                    break;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "叫到了:" + index);
                index++;
            }
        }
    }

    // 这种方式是okay
    private static class Runnable2 implements Runnable {
        private static final int MAX = 500;
        private int index = 1;

        @Override
        public void run() {
            while (true) {
                if (hasNoMoreTicket())
                    break;
            }
        }

        // 重构代码，重构处一个函数
        private synchronized boolean hasNoMoreTicket() {
            if (index > MAX) {
                return true;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "叫到了:" + index);
            index++;
            return false;
        }
    }

    // 这种方式是okay
    private static class Runnable3 implements Runnable {
        private static final int MAX = 500;
        private int index = 1;

        @Override
        public void run() {
            while (true) {
                if (hasNoMoreTicket())
                    break;
            }
        }

        // 重构代码，重构处一个函数
        private boolean hasNoMoreTicket() {
            synchronized (this) {
                if (index > MAX) {
                    return true;
                }
                try {
                    Thread.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "叫到了:" + index);
                index++;
                return false;
            }
        }
    }

    private static class ThisClassLock {
        synchronized void m1() {
            System.out.println("m1 " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized void m2() {
            System.out.println("m2 " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void m3() {
            System.out.println("m3 no synchronized " + Thread.currentThread().getName());
            try {
                Thread.sleep(100_100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
