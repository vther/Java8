package com.vther.java.concurrent.util;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Semaphore字面意思是信号量。他主要用于控制有限的资源的访问数量。
 * http://blog.csdn.net/winwill2012/article/details/71624925
 */
public class _04_Semaphore {
    public static void main(String[] args) throws InterruptedException {
        Semaphore wc = new Semaphore(3, true); // 3个坑位
        for (int i = 1; i <= 6; i++) {
            Thread t = new Thread(new Person("第" + i + "个人", wc));
            t.start();
            Thread.sleep(new Random().nextInt(300));
        }
    }

    static class Person implements Runnable {
        private String name;
        private Semaphore wc;

        Person(String name, Semaphore wc) {
            this.name = name;
            this.wc = wc;
        }

        public void run() {
            System.out.print(name + "：憋死老子了!");
            if (wc.availablePermits() > 0) {
                System.out.println("天助我也，有坑位！");
            } else {
                System.out.println("卧槽，没坑位了，等会儿吧...");
            }
            try {
                wc.acquire(); //申请坑位
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：终于轮到我了，拉屎就是爽！");
            try {
                Thread.sleep(new Random().nextInt(1000)); // 模拟上厕所时间。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：拉完了，好臭!");
            wc.release();
        }
    }
}
