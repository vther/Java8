package com.vther.java.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch类  http://blog.csdn.net/winwill2012/article/details/71624562
 * <p>
 * CountDownLatch顾名思义：倒计数锁存器。没错，他就是一个计数器，并且是倒着计数的。他的应用场景如下：
 * <p>
 * 一个任务A，他需要等待其他的一些任务都执行完毕之后它才能执行。就比如说赛跑的时候，发令员需要等待所有运动员都准备好了才能发令，否则不被K才怪嘞！
 */
public class _02_CountDownLatch {
    private static final int RUNNER_NUMBER = 5; // 运动员个数
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // 用于判断发令之前运动员是否已经完全进入准备状态，需要等待5个运动员，所以参数为5
        CountDownLatch readyLatch = new CountDownLatch(RUNNER_NUMBER);
        // 用于判断裁判是否已经发令，只需要等待一个裁判，所以参数为1
        CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < RUNNER_NUMBER; i++) {
            Thread t = new Thread(new Runner((i + 1) + "号运动员", readyLatch, startLatch));
            t.start();
        }
        try {
            readyLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startLatch.countDown();
        System.out.println("裁判：所有运动员准备完毕，开始...");
    }

    static class Runner implements Runnable {
        private CountDownLatch readyLatch;
        private CountDownLatch startLatch;
        private String name;

        Runner(String name, CountDownLatch readyLatch, CountDownLatch startLatch) {
            this.name = name;
            this.readyLatch = readyLatch;
            this.startLatch = startLatch;
        }

        public void run() {
            int readyTime = RANDOM.nextInt(1000);
            System.out.println(name + "：我需要" + readyTime + "秒时间准备.");
            try {
                Thread.sleep(readyTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：我已经准备完毕.");
            readyLatch.countDown();
            try {
                startLatch.await();  // 等待裁判发开始命令
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：开跑...");
        }
    }
}
