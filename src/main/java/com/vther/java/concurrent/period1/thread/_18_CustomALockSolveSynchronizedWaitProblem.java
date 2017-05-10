package com.vther.java.concurrent.period1.thread;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// 自定义一个锁，解决synchronized超长会把其他线程锁定的问题（无法打断等）
public class _18_CustomALockSolveSynchronizedWaitProblem {


    public static void main(String[] args) {
        // 如果一个线程抢到synchronized但执行时间过程，其他线程会无限等待
        //_01_synchronizedProblem();

        _02_customLock1_lock();
        _03_customLock2_lockTimeOut();
    }


    private static void _01_synchronizedProblem() {
        new Thread(_18_CustomALockSolveSynchronizedWaitProblem::m1).start();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(_18_CustomALockSolveSynchronizedWaitProblem::m1);
        t2.start();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
        // t2 虽然被打断，却仍旧无法停下来
        System.out.println("t2.isInterrupted() -> " + t2.isInterrupted());
    }

    private static synchronized void m1() {
        System.out.println(Thread.currentThread());
        while (true) {

        }
    }


    private static void _02_customLock1_lock() {
        Lock lock = new BooleanLock();

        Stream.of("T1", "T2", "T3", "T4").forEach(name -> new Thread(() -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread() + " get lock");

                Thread.sleep(4000L);// do some business
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread() + " release lock");
            }
        }, name).start());

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println("lock.getBlockedThread -> " + lock.getBlockedSize());
        System.out.println("lock.getBlockedThread -> " + lock.getBlockedThread());
    }

    private static void _03_customLock2_lockTimeOut() {
        Lock lock = new BooleanLock();

        Stream.of("T1", "T2", "T3", "T4").forEach(name -> new Thread(() -> {
            try {
                lock.lock(1500L);
                System.out.println(Thread.currentThread() + " get lock");

                Thread.sleep(4000L);// do some business
            } catch (InterruptedException | Lock.TimeOutException e) {
                System.out.println(Thread.currentThread() + " time out");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread() + " release lock");
            }
        }, name).start());

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println("lock.getBlockedThread -> " + lock.getBlockedSize());
        System.out.println("lock.getBlockedThread -> " + lock.getBlockedThread());
    }

    private interface Lock {
        void lock() throws InterruptedException;

        void lock(long timeout) throws InterruptedException, TimeOutException;

        void unlock();

        Collection<Thread> getBlockedThread();

        int getBlockedSize();

        class TimeOutException extends Exception {
            TimeOutException(String message) {
                super(message);
            }
        }
    }

    private static class BooleanLock implements Lock {
        private boolean locked = false;
        private List<Thread> blockThreads = new ArrayList<>();
        private Thread holdLockThread;

        @Override
        public synchronized void lock() throws InterruptedException {
            while (locked) {
                blockThreads.add(Thread.currentThread());
                this.wait();//如果锁已经被获取，该线程就等待
            }
            locked = true;
            blockThreads.remove(Thread.currentThread());
            holdLockThread = Thread.currentThread();
        }

        @Override
        public synchronized void lock(long timeout) throws InterruptedException, TimeOutException {
            if (timeout <= 0) {
                lock();
            } else {
                while (locked) {
                    long timeoutTime = System.currentTimeMillis() + timeout;
                    blockThreads.add(Thread.currentThread());
                    this.wait(timeout);//如果其他线程获取到锁，该线程就等待
                    if ((System.currentTimeMillis() - timeoutTime) > 0) {
                        blockThreads.remove(Thread.currentThread());
                        throw new TimeOutException("Time out");
                    }
                }
                locked = true;
                blockThreads.remove(Thread.currentThread());
                holdLockThread = Thread.currentThread();
            }
        }

        @Override
        public synchronized void unlock() {
            if (holdLockThread == Thread.currentThread()) {// 防止没有权限的人解锁
                locked = false;
                holdLockThread = null;
                this.notify();// 通知一个线程来抢锁；使用this.notifyAll()会让所有的来抢锁，效率低
            } else {
                System.out.println(Thread.currentThread() + " do not have the lock!");
            }
        }

        @Override
        public Collection<Thread> getBlockedThread() {
            return Collections.unmodifiableList(blockThreads);//将list置为不可修改，防止外部置为空以不正当抢锁
        }

        @Override
        public int getBlockedSize() {
            return blockThreads.size();
        }
    }

}
