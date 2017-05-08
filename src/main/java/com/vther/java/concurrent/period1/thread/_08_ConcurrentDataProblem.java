package com.vther.java.concurrent.period1.thread;

// 线程安全问题
public class _08_ConcurrentDataProblem {


    public static void main(String[] args) {

        //Runnable runnable = new UnsafeTicketRunnable();
        Runnable runnable = new SafeTicketRunnable();
        Thread t1 = new Thread(runnable, "一号机器");
        Thread t2 = new Thread(runnable, "二号机器");
        Thread t3 = new Thread(runnable, "三号机器");
        t1.start();
        t2.start();
        t3.start();
    }


    //private static class UnsafeTicketRunnable implements Runnable {
    //    private static final int MAX = 500;
    //    private int index = 1;
    //
    //    @Override
    //    public void run() {
    //        while (true) {
    //            if (index > MAX) {
    //                break;
    //            }
    //            try {
    //                Thread.sleep(5);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //            System.out.println(Thread.currentThread().getName() + "叫到了:" + index);
    //            index++;
    //        }
    //    }
    //}

    private static class SafeTicketRunnable implements Runnable {
        private static final int MAX = 500;
        private final Object LOCK = new Object();
        private int index = 1;

        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
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
    }
}
