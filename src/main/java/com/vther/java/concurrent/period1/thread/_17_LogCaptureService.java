package com.vther.java.concurrent.period1.thread;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 模拟从一个主机调用10个机器上搜集异步搜集日志，最多启用4个线程。
public class _17_LogCaptureService {


    // 锁
    private static final LinkedList<Object> CONTROLS = new LinkedList<>();

    public static void main(String[] args) {

        List<Thread> workList = Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(threadName -> new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + " start capture");
                    synchronized (CONTROLS) {
                        while (CONTROLS.size() > 3) {
                            try {
                                CONTROLS.wait();//
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    CONTROLS.addLast(new Object());

                    System.out.println(Thread.currentThread().getName() + " is working");
                    try {
                        Thread.sleep(5000L);// 采集日志需要5s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (CONTROLS) {
                        System.out.println(Thread.currentThread().getName() + " end capture");
                        CONTROLS.removeFirst();
                        CONTROLS.notify();// 这里使用notify和notifyAll都可以，但是notify会效率更高，
                        // notifyAll会让所有等待的再次都抢锁，notify只有一个抢
                    }
                }, threadName)).collect(Collectors.toList());


        // 注意 start和join不能放在一个consumer里面，不然就变成了逐个执行，因为主线程会等前一个线程join掉才会开始下一个线程
        workList.forEach(Thread::start);

        workList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
