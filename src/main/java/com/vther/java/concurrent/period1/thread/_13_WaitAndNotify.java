package com.vther.java.concurrent.period1.thread;

/**
 * wait()、notify()、notifyAll()是三个定义在Object类里的方法，可以用来控制线程的状态。
 * 这三个方法最终调用的都是jvm级的native方法。随着jvm运行平台的不同可能有些许差异。
 * <p>
 * 如果对象调用了wait方法就会使持有该对象的线程把该对象的控制权交出去，然后使该线程处于等待状态。
 * 如果对象调用了notify方法就会通知某个正在等待这个对象的控制权的线程可以继续运行。
 * 如果对象调用了notifyAll方法就会通知所有等待这个对象控制权的线程继续运行。
 */
public class _13_WaitAndNotify {


    private final Object LOCK = new Object();
    private int i = 1;
    private volatile boolean isProduced = false;

    public static void main(String[] args) {
        _13_WaitAndNotify waitAndNotify = new _13_WaitAndNotify();
        new Thread(() -> {
            while (true) {
                try {
                    waitAndNotify.produce();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    waitAndNotify.consume();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void produce() throws InterruptedException {
        synchronized (LOCK) {
            if (isProduced) {
                LOCK.wait();//已经生产，等待消费者消费
            } else {
                i++;
                System.out.println("produce -> " + i);
                isProduced = true;// 表示已经生产
                LOCK.notify();// 通知消费者去消费
            }
        }
    }


    private void consume() throws InterruptedException {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("consume -> " + i);
                isProduced = false;// 消费了置为false
                LOCK.notify();//通知生产者去生产
            } else {
                LOCK.wait();// 没有生产，等待生产者生产
            }
        }
    }
}
