package com.vther.java.concurrent.period1.thread;

// 一句话总结：synchronized中间的代码会单线程执行
public class _09_HowSynchronizedWork {


    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        Runnable runnable = () -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(200_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(runnable, "Thread-1");
        Thread t2 = new Thread(runnable, "Thread-2");
        Thread t3 = new Thread(runnable, "Thread-3");
        t1.start();
        t2.start();
        t3.start();

        // 一：打开jconsole，可以看到
        //    名称: Thread-1
        //    状态: TIMED_WAITING

        //    名称: Thread-2
        //    状态: java.lang.Object@50f50baa上的BLOCKED, 拥有者: Thread-1
        //    总阻止数: 1, 总等待数: 0

        //    名称: Thread-3
        //    状态: java.lang.Object@50f50baa上的BLOCKED, 拥有者: Thread-1
        //    总阻止数: 1, 总等待数: 0

        //  总结 ---> Thread-1 获取到了锁，Thread-2和Thread-3都在等待获取锁

        // 二：打开cmd窗口，使用jps和jstack可以看到同样结果
        /*
        C:\Users\Wither>jps
        10196 Jps
        14708
        4196 _09_HowSynchronizedWork
        5236 Launcher
        8116 RemoteMavenServer

        C:\Users\Wither>jstack 4196
        2017-05-08 23:03:27
        Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.111-b14 mixed mode):

        "DestroyJavaVM" #15 prio=5 os_prio=0 tid=0x000000000149e000 nid=0x98c waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Thread-3" #14 prio=5 os_prio=0 tid=0x000000001b166800 nid=0x29bc waiting for monitor entry [0x000000001bbef000]
        java.lang.Thread.State: BLOCKED (on object monitor)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork.lambda$main$0(_09_HowSynchronizedWork.java:14)
        - waiting to lock <0x00000000d5c9e9c8> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork$$Lambda$1/1747585824.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

        "Thread-2" #13 prio=5 os_prio=0 tid=0x000000001b166000 nid=0x2520 waiting for monitor entry [0x000000001baef000]
        java.lang.Thread.State: BLOCKED (on object monitor)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork.lambda$main$0(_09_HowSynchronizedWork.java:14)
        - waiting to lock <0x00000000d5c9e9c8> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork$$Lambda$1/1747585824.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)

        "Thread-1" #12 prio=5 os_prio=0 tid=0x000000001b163000 nid=0x1ae4 waiting on condition [0x000000001b9ef000]
        java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork.lambda$main$0(_09_HowSynchronizedWork.java:14)
        - locked <0x00000000d5c9e9c8> (a java.lang.Object)
        at com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork$$Lambda$1/1747585824.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:745)
        */


        // 三：打开cmd窗口，使用javap可以看到如下 javap -c _09_HowSynchronizedWork.class
        // 分析：aload_1，aload_2，aload_3依次执行
        /*
        E:\Develop\intel-workspace\Java8\target\classes\com\vther\java\concurrent\period1\thread>javap -c _09_HowSynchronizedWork.class
        Compiled from "_09_HowSynchronizedWork.java"
        public class com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork {
          public com.vther.java.concurrent.period1.thread._09_HowSynchronizedWork();
            Code:
               0: aload_0
               1: invokespecial #1                  // Method java/lang/Object."<init>":()V
               4: return

          public static void main(java.lang.String[]);
            Code:
               0: invokedynamic #2,  0              // InvokeDynamic #0:run:()Ljava/lang/Runnable;
               5: astore_1
               6: new           #3                  // class java/lang/Thread
               9: dup
              10: aload_1
              11: ldc           #4                  // String Thread-1
              13: invokespecial #5                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;Ljava/lang/String;)V
              16: astore_2
              17: new           #3                  // class java/lang/Thread
              20: dup
              21: aload_1
              22: ldc           #6                  // String Thread-2
              24: invokespecial #5                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;Ljava/lang/String;)V
              27: astore_3
              28: new           #3                  // class java/lang/Thread
              31: dup
              32: aload_1
              33: ldc           #7                  // String Thread-3
              35: invokespecial #5                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;Ljava/lang/String;)V
              38: astore        4
              40: aload_2
              41: invokevirtual #8                  // Method java/lang/Thread.start:()V
              44: aload_3
              45: invokevirtual #8                  // Method java/lang/Thread.start:()V
              48: aload         4
              50: invokevirtual #8                  // Method java/lang/Thread.start:()V
              53: return

          static {};
            Code:
               0: new           #15                 // class java/lang/Object
               3: dup
               4: invokespecial #1                  // Method java/lang/Object."<init>":()V
               7: putstatic     #9                  // Field LOCK:Ljava/lang/Object;
              10: return
        }

         */
    }


}
