package com.vther.java.concurrent.period1.thread;

public class _01_LifeCycleOfThread {

    public static void main(String[] args) {
        Thread t1 = new Thread("aa") {
            @Override
            public void run() {
                System.out.println("Thread start ----");
                try {
                    Thread.sleep(1000 * 2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread  end  ----");
            }

        };
        t1.start();

        // 使用jconsole可以查看运行的java线程

        //  线程的生命周期-->线程是一个动态执行的过程，它也有一个从产生到死亡的过程。

        //   Thread的生成周期 new  -> start -> runnable(可执行状态) ---Dispatch等待系统调度---> running --->  blocked

        //  (1)生命周期的五种状态
        //      新建（new Thread）
        //      当创建Thread类的一个实例（对象）时，此线程进入新建状态（未被启动）。
        //      例如：Thread t1=new Thread();
        //  (2)就绪（runnable）
        //      线程已经被启动，正在等待被分配给CPU时间片，也就是说此时线程正在就绪队列中排队等候得到CPU资源。例如：t1.start();
        //  (3)运行（running）
        //      线程获得CPU资源正在执行任务（run()方法），此时除非此线程自动放弃CPU资源或者有优先级更高的线程进入，线程将一直运行到结束。
        //  (4)死亡（dead）
        //      当线程执行完毕或被其它线程杀死，线程就进入死亡状态，这时线程不可能再进入就绪状态等待执行。
        //          自然终止：正常运行run()方法后终止
        //          异常终止：调用stop()方法让一个线程终止运行
        //  (5)堵塞（blocked）
        //      由于某种原因导致正在运行的线程让出CPU并暂停自己的执行，即进入堵塞状态。
        //      正在睡眠：用sleep(long t) 方法可使线程进入睡眠方式。一个睡眠着的线程在指定的时间过去可进入就绪状态。
        //      正在等待：调用wait()方法。（调用modify()方法回到就绪状态）
        //      被另一个线程所阻塞：调用suspend()方法。（调用resume()方法恢复）


    }
}
