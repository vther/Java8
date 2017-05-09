package com.vther.java.concurrent.period1.thread;


import java.util.stream.Stream;

// 多个生产者消费者的时候，这种方式就会有问题，但是通过jstack看不到有死锁
/*
    一个可能的执行流程如下：
    P2 produce -> 2
    C1 consume -> 2
            P2 produce wait
    P2 produce -> 3
    C2 consume -> 3
            C1 consume wait
            P1 produce wait
    P1 produce -> 4
            P2 produce wait
    会让四个线程都进入到wait状态，然后出现程序不会停下来的情况
 */
public class _14_MultiProducerConsumerProblem {


    private final Object LOCK = new Object();
    private int i = 1;
    private volatile boolean isProduced = false;

    public static void main(String[] args) {
        _14_MultiProducerConsumerProblem notify = new _14_MultiProducerConsumerProblem();
        Stream.of("P1", "P2").forEach(
                n -> new Thread(() -> {
                    while (true) {
                        notify.produce();
                    }
                }, n).start());

        Stream.of("C1", "C2").forEach(
                n -> new Thread(() -> {
                    while (true) {
                        notify.consume();
                    }
                }, n).start());
    }

    private void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();//已经生产，等待消费者消费
                    System.out.println(Thread.currentThread().getName() + " produce wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println(Thread.currentThread().getName() + " produce -> " + i);
                isProduced = true;// 表示已经生产
                LOCK.notify();// 通知消费者去消费
            }
        }
    }

    private void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println(Thread.currentThread().getName() + " consume -> " + i);
                isProduced = false;// 消费了置为false
                LOCK.notify();//通知生产者去生产
            } else {
                try {
                    LOCK.wait();// 没有生产，等待生产者生产
                    System.out.println(Thread.currentThread().getName() + " consume wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    "DestroyJavaVM" #16 prio=5 os_prio=0 tid=0x0000000002cbe000 nid=0x1304 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-3" #15 prio=5 os_prio=0 tid=0x000000001b07c000 nid=0x2de8 in Object.wait() [0x000000001bc1f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5ca04e0> (a java.lang.Object)
        at java.lang.Object.wait(Object.java:502)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.consume(_14_ProducerConsumer.java:56)
        - locked <0x00000000d5ca04e0> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.lambda$null$2(_14_ProducerConsumer.java:25)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer$$Lambda$4/2074407503.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

"Thread-2" #14 prio=5 os_prio=0 tid=0x000000001b07b800 nid=0x38c4 in Object.wait() [0x000000001bb1f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5ca04e0> (a java.lang.Object)
        at java.lang.Object.wait(Object.java:502)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.consume(_14_ProducerConsumer.java:56)
        - locked <0x00000000d5ca04e0> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.lambda$null$2(_14_ProducerConsumer.java:25)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer$$Lambda$4/2074407503.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

"Thread-1" #13 prio=5 os_prio=0 tid=0x000000001b078800 nid=0x728 in Object.wait() [0x000000001ba1f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5ca04e0> (a java.lang.Object)
        at java.lang.Object.wait(Object.java:502)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.produce(_14_ProducerConsumer.java:34)
        - locked <0x00000000d5ca04e0> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.lambda$null$0(_14_ProducerConsumer.java:18)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer$$Lambda$2/1149319664.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

"Thread-0" #12 prio=5 os_prio=0 tid=0x000000001b077800 nid=0x14b4 in Object.wait() [0x000000001b91f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5ca04e0> (a java.lang.Object)
        at java.lang.Object.wait(Object.java:502)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.produce(_14_ProducerConsumer.java:34)
        - locked <0x00000000d5ca04e0> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer.lambda$null$0(_14_ProducerConsumer.java:18)
        at com.vther.java.concurrent.period1.thread._14_ProducerConsumer$$Lambda$2/1149319664.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)
     */
}
