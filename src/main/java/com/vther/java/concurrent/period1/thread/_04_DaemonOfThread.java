package com.vther.java.concurrent.period1.thread;


public class _04_DaemonOfThread {
    public static void main(String[] args) {
        // 例如：如果线程a拉起了线程b
        // 守护线程：意思是b是否会守护线程a
        //
        //  1, b.setDaemon(true|false);
        //     守护线程设为false意味着：a会等待b执行完毕才结束
        //             设为true意味着：a结束，b也会随之结束
        //  2, t.setDaemon要在start方法前调用，否则会抛出IllegalThreadStateException
        //  3, 默认不设置，b是非守护线程

        Thread t = new Thread("Thread0001") {
            @Override
            public void run() {
                try {
                    System.out.println("Thread0001 run");
                    Thread.sleep(10000L);
                    System.out.println("Thread0001 done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        t.start();

        System.out.println("--------I am not blocked------------");

    }
}
