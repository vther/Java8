package com.vther.java.concurrent.period1.thread;


public class _13_WaitAndNotify {


    private final Object LOCK = new Object();
    private int i = 1;
    private volatile boolean isProduced = false;

    public static void main(String[] args) {
        _13_WaitAndNotify notify = new _13_WaitAndNotify();
        new Thread(() -> {
            while (true) {
                try {
                    notify.produce();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    notify.consume();
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
