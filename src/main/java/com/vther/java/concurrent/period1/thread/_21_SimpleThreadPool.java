package com.vther.java.concurrent.period1.thread;


import java.util.LinkedList;
import java.util.stream.IntStream;


public class _21_SimpleThreadPool {

    private static final int DEFAULT_SIZE = 10;
    private final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private final LinkedList<WorkTask> WORK_QUEUE = new LinkedList<>();
    private final String THREAD_NAME_PREFIX = "SimpleThreadPool-";
    private ThreadGroup threadGroup = new ThreadGroup("threadGroup");

    private int sequence = 0;

    private _21_SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    private _21_SimpleThreadPool(int size) {
        for (int i = 0; i < size; i++) {
            WorkTask workTask = new WorkTask(threadGroup, THREAD_NAME_PREFIX + sequence++);
            workTask.start();
            WORK_QUEUE.add(workTask);
        }
    }

    public static void main(String[] args) {
        _21_SimpleThreadPool simpleThreadPool = new _21_SimpleThreadPool();
        IntStream intStream = IntStream.rangeClosed(1, 40);
        intStream.forEach(i -> simpleThreadPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " " + i + " start");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + i + " end");
        }));
    }

    private void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            while (TASK_QUEUE.size() > 10) {
                try {
                    TASK_QUEUE.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notify();
        }
    }

    enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD;
    }

    private class WorkTask extends Thread {

        private TaskState taskState = TaskState.FREE;

        WorkTask(ThreadGroup threadGroup, String threadName) {
            super(threadGroup, threadName);
        }

        @Override
        public void run() {
            OUTER:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    System.out.println(Thread.currentThread().getName() + " execute");
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                    System.out.println(Thread.currentThread().getName() + " done");
                }
                synchronized (TASK_QUEUE) {
                    TASK_QUEUE.notify();
                }
            }
        }

        void close() {
            this.taskState = TaskState.DEAD;
        }
    }
}


