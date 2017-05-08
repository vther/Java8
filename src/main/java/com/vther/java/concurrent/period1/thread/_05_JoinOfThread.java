package com.vther.java.concurrent.period1.thread;


public class _05_JoinOfThread {
    public static void main(String[] args) {

        // 例如：如果线程a拉起了线程b

        //  1, a.join()：线程a会等待线程b执行完毕才开始执行
        //  2, a.join()必须设置在t.start()后才有效

        System.out.println("Main Thread start");

        Thread t = new Thread("Sub Thread") {
            @Override
            public void run() {
                try {
                    System.out.println("Sub Thread run");
                    Thread.sleep(5000L);
                    System.out.println("Sub Thread done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main Thread done");

    }
}
