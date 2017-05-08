package com.vther.java.concurrent.period1.thread;


import com.vther.java.completablefuture.TimeUtils;

import java.time.Duration;
import java.time.Instant;

// 优雅结束一个线程
public class _07_GracefullyStopAThread {


    public static void main(String[] args) {
        Instant start = Instant.now();
        System.out.println("Main Thread start");
        // 三种方式去结束一个线程
        function1();
        function2();
        function3();
        System.out.println("Main Thread end, costs" + Duration.between(start, Instant.now()).getSeconds() + "s");
    }

    // 在线程中使用flag的形式去停止线程，缺点：需要将业务逻辑放在循环里面才可以
    private static void function1() {
        WorkThread1 workThread1 = new WorkThread1();
        workThread1.start();

        TimeUtils.sleep3Seconds();// 表示等待任务执行3秒，3秒执行不完就结束线程
        workThread1.shutdown();
    }

    // 使用打断的方式去停止线程，缺点：需要将业务逻辑放在循环里面才可以
    private static void function2() {
        WorkThread2 workThread2 = new WorkThread2();
        workThread2.start();

        TimeUtils.sleep3Seconds();// 表示等待任务执行3秒，3秒执行不完就结束线程
        workThread2.interrupt();
    }

    // 最佳方式
    private static void function3() {
        ThreadService threadService = new ThreadService();
        threadService.execute(() -> {
            System.out.println("runnable in wrapThread start");
            TimeUtils.sleepSeconds(3000);//模拟一个非常耗时的工作
            System.out.println("runnable in wrapThread end");
        });

        TimeUtils.sleep3Seconds();// 表示等待任务执行3秒，3秒执行不完就结束线程
        threadService.shutdown();
    }

    private static class WorkThread1 extends Thread {
        // 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值。
        private volatile boolean flag = true;

        @Override
        public void run() {
            System.out.println("WorkThread1 start");
            while (flag) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("WorkThread1 end");
        }

        void shutdown() {
            this.flag = false;
        }
    }

    private static class WorkThread2 extends Thread {
        @Override
        public void run() {
            System.out.println("WorkThread2 start");
            while (true) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    break;//return
                }
            }
            System.out.println("WorkThread2 end");
        }
    }


    private static class ThreadService {
        Thread wrapThread;// 包装线程

        void execute(Runnable runnable) {
            // 1，定义包装线程
            wrapThread = new Thread(() -> {
                System.out.println("wrapThread start");
                //2，包装线程会拉起一个真正的执行线程，去执行外部传进来的任务
                Thread runner = new Thread(runnable);
                //3，设置执行线程为包装线程的守护线程（包装线程死 -> 执行线程死）
                runner.setDaemon(true);
                //4，启动执行线程
                runner.start();
                try {
                    // 5，设置执行线程先执行完毕后，包装线程才会执行
                    runner.join();
                    System.out.println("I will not be printed");//这段代码不会执行，因为程序会卡在runner.join()那里
                } catch (InterruptedException e) {
                    // 6，如果包装线程被打断，会跳入这里，包装线程会死掉，执行线程也会死掉
                    System.out.println("InterruptedException in wrapThread");
                }
                System.out.println("wrapThread end");
            });
            wrapThread.start();
        }

        void shutdown() {
            // 打断包装线程，包装线程会死掉，执行线程也会死掉
            wrapThread.interrupt();
        }
    }
}
