package com.vther.java.concurrent.period1.thread;

// static class锁
public class _12_DeadLock {


    public static void main(String[] args) {
        Service1 service1 = new Service1();
        Service2 service2 = new Service2(service1);
        service1.setService2(service2);
        new Thread(() -> {
            while (true) {
                service2.s2();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                service1.m1();
            }
        }).start();

        // jps 然后 jstack 可以找到        Found 1 deadlock.
        /*
        "Thread-1" #13 prio=5 os_prio=0 tid=0x000000001b066000 nid=0x2ae8 waiting for monitor entry [0x000000001b9fe000]
           java.lang.Thread.State: BLOCKED (on object monitor)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service2.s1(_12_DeadLock.java:56)
                - waiting to lock <0x00000000d5ca6738> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service1.m1(_12_DeadLock.java:35)
                - locked <0x00000000d5ca26e0> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock.lambda$main$1(_12_DeadLock.java:18)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$$Lambda$2/1149319664.run(Unknown Source)
                at java.lang.Thread.run(Thread.java:745)

        "Thread-0" #12 prio=5 os_prio=0 tid=0x000000001b063000 nid=0x2b94 waiting for monitor entry [0x000000001b8ff000]
           java.lang.Thread.State: BLOCKED (on object monitor)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service1.m2(_12_DeadLock.java:41)
                - waiting to lock <0x00000000d5ca26e0> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service2.s2(_12_DeadLock.java:63)
                - locked <0x00000000d5ca6738> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock.lambda$main$0(_12_DeadLock.java:13)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$$Lambda$1/1747585824.run(Unknown Source)
                at java.lang.Thread.run(Thread.java:745)


                Found one Java-level deadlock:
        =============================
        "Thread-1":
          waiting to lock monitor 0x00000000182e0d38 (object 0x00000000d5ca6738, a java.lang.Object),
          which is held by "Thread-0"
        "Thread-0":
          waiting to lock monitor 0x00000000182e2288 (object 0x00000000d5ca26e0, a java.lang.Object),
          which is held by "Thread-1"

        Java stack information for the threads listed above:
        ===================================================
        "Thread-1":
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service2.s1(_12_DeadLock.java:56)
                - waiting to lock <0x00000000d5ca6738> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service1.m1(_12_DeadLock.java:35)
                - locked <0x00000000d5ca26e0> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock.lambda$main$1(_12_DeadLock.java:18)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$$Lambda$2/1149319664.run(Unknown Source)
                at java.lang.Thread.run(Thread.java:745)
        "Thread-0":
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service1.m2(_12_DeadLock.java:41)
                - waiting to lock <0x00000000d5ca26e0> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$Service2.s2(_12_DeadLock.java:63)
                - locked <0x00000000d5ca6738> (a java.lang.Object)
                at com.vther.java.concurrent.period1.thread._12_DeadLock.lambda$main$0(_12_DeadLock.java:13)
                at com.vther.java.concurrent.period1.thread._12_DeadLock$$Lambda$1/1747585824.run(Unknown Source)
                at java.lang.Thread.run(Thread.java:745)

        Found 1 deadlock.
         */
    }


    private static class Service1 {
        private final Object LOCK = new Object();
        private Service2 service2;

        public void setService2(Service2 service2) {
            this.service2 = service2;
        }

        void m1() {
            synchronized (LOCK) {
                System.out.println("Service1 --- m1 " + Thread.currentThread().getName());
                service2.s1();
            }
        }

        void m2() {
            synchronized (LOCK) {
                System.out.println("Service1 --- m2 " + Thread.currentThread().getName());
            }
        }
    }

    private static class Service2 {
        private final Object LOCK = new Object();
        private Service1 service1;

        public Service2(Service1 service1) {
            this.service1 = service1;
        }

        void s1() {
            synchronized (LOCK) {
                System.out.println("Service2 --- s1 " + Thread.currentThread().getName());
            }
        }

        void s2() {
            synchronized (LOCK) {
                System.out.println("Service2 --- s2 " + Thread.currentThread().getName());
                service1.m2();
            }
        }
    }
}
