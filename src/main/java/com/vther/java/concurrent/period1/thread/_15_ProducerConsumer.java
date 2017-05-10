package com.vther.java.concurrent.period1.thread;


import java.util.stream.Stream;


public class _15_ProducerConsumer {


    private static final Object LOCK = new Object();
    private int i = 1;
    private volatile boolean isProduced = false;

    public static void main(String[] args) {
        _15_ProducerConsumer notify = new _15_ProducerConsumer();

        Stream.of("P1", "P2", "P3").forEach(
                n -> new Thread(() -> {
                    while (true) {
                        notify.produce();
                    }
                }, n).start());

        Stream.of("C1", "C2", "C3", "C4").forEach(
                n -> new Thread(() -> {
                    while (true) {
                        notify.consume();
                    }
                }, n).start());
    }

    private void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();//已经生产，等待消费者消费
                    //System.out.println(Thread.currentThread().getName() + " produce wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println(Thread.currentThread().getName() + " produce -> " + i);
            isProduced = true;// 表示已经生产
            LOCK.notifyAll();// 通知消费者去消费
        }
    }

    private void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();// 没有生产，等待生产者生产
                    //System.out.println(Thread.currentThread().getName() + " consume wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " consume -> " + i);
            isProduced = false;// 消费了置为false
            LOCK.notifyAll();//通知生产者去生产
        }
    }

}
