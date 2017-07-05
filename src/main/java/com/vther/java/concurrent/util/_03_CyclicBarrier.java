package com.vther.java.concurrent.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier翻译过来就是：循环的屏障。什么是循环？可以重复利用呗，对这个类就是一个可以重复利用的屏障类。CyclicBarrier主要用于一组固定大小的线程之间，各个线程之间相互等待，当所有线程都完成某项任务之后，才能执行之后的任务。
 * 如下场景：
 * <p>
 * 有若干个线程都需要向一个数据库写数据，但是必须要所有的线程都讲数据写入完毕他们才能继续做之后的事情。
 * 分析一下这个场景的特征：
 * <p>
 * 各个线程都必须完成某项任务(写数据)才能继续做后续的任务；
 * 各个线程需要相互等待，不能独善其身。
 * 这种场景便可以利用CyclicBarrier来完美解决。
 * <p>
 * 常用函数
 * <p>
 * 本节介绍CyclicBarrier的基本操作函数。
 * <p>
 * 构造函数
 * <p>
 * 有两种类型的构造函数，函数签名分别如下：
 * <p>
 * public CyclicBarrier(int parties, Runnable barrierAction)
 * public CyclicBarrier(int parties): 参数parties表示一共有多少线程参与这次“活动”，参数barrierAction是可选的，用来指定当所有线程都完成这些必须的“神秘任务”之后需要干的事情，所以barrierAction这里的动作在一个相互等待的循环内只会执行一次。
 * <p>
 * getParties函数: getParties用来获取当前的CyclicBarrier一共有多少线程参数与，函数签名如下：
 * <p>
 * public int getParties() 返回参与“活动”的线程个数。
 * <p>
 * await函数  await函数用来执行等待操作，有两种类型的函数签名：
 * public int await() throws InterruptedException, BrokenBarrierException
 * public int await(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException
 * 第一个函数是一个无参函数，第二个函数可以指定等待的超时时间。它们的作用是：一直等待知道所有参与“活动”的线程都调用过await函数，如果当前线程不是即将调用await函数的的最后一个线程，当前线程将会被挂起，直到下列某一种情况发生：
 * <p>
 * 最后一个线程调用了await函数；
 * 某个线程打断了当前线程；
 * 某个线程打断了其他某个正在等待的线程；
 * 其他某个线程等待时间超过给定的超时时间；
 * 其他某个线程调用了reset函数。
 * 如果等待过程中线程被打断了，则会抛出InterruptedException异常；
 * 如果等待过程中出现下列情况中的某一种情况，则会抛出BrokenBarrierException异常：
 * <p>
 * 其他线程被打断了；
 * 当前线程等待超时了；
 * 当前CyclicBarrier被reset了；
 * 等待过程中CyclicBarrier损坏了；
 * 构造函数中指定的barrierAction在执行过程中发生了异常。
 * 如果等待时间超过给定的最大等待时间，则会抛出TimeoutException异常，并且这个时候其他已经嗲用过await函数的线程将会继续后续的动作。
 * <p>
 * 返回值：返回当前线程在调用过await函数的所以线程中的编号，编号为parties-1的表示第一个调用await函数，编号为0表示是最后一个调用await函数。
 * <p>
 * isBroken函数: 给函数用来判断barrier是否已经损坏，函数签名如下： public boolean isBroken()
 * 如果因为任何原因被损坏返回true，否则返回false。
 * <p>
 * reset函数
 * 顾名思义，这个函数用来重置barrier，函数签名如下： public void reset()
 * 如果调用了该函数，则在等待的线程将会抛出BrokenBarrierException异常。
 * <p>
 * getNumberWaiting函数
 * 该函数用来获取当前正在等待该barrier的线程数，函数签名如下： public int getNumberWaiting()
 */
public class _03_CyclicBarrier {
    private static final int THREAD_NUMBER = 5;
    private static final Random RANDOM = new Random();

    /**
     * 模拟实验
     * <p>
     * 下面用代码实现下面的场景：
     * <p>
     * 有5个线程都需要向一个数据库写数据，但是必须要所有的线程都讲数据写入完毕他们才能继续做之后的事情。
     */
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUMBER, new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getId() + "：我宣布，所有小伙伴写入数据完毕");
            }
        });
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread t = new Thread(new Worker(barrier));
            t.start();
        }
    }

    static class Worker implements Runnable {
        private CyclicBarrier barrier;

        Worker(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            int time = RANDOM.nextInt(1000);
            System.out.println(Thread.currentThread().getId() + "：我需要" + time + "毫秒时间写入数据.");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "：写入数据完毕，等待其他小伙伴...");
            try {
                barrier.await(); // 等待所有线程都调用过此函数才能进行后续动作
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "：所有线程都写入数据完毕，继续干活...");
        }
    }
}
