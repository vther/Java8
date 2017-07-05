package com.vther.java.concurrent.util;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

/**
 * http://blog.csdn.net/defonds/article/details/44021605/ <br>
 * <br>
 * BlockingQueue 用法<br>
 * ----  通常用于一个线程生产对象，而另外一个线程消费这些对象的场景。
 * 下图是对这个原理的阐述：
 * 一个线程将会持续生产新对象并将其插入到队列之中，直到队列达到它所能容纳的临界点。
 * 也就是说，它是有限的。如果该阻塞队列到达了其临界点，负责生产的线程将会在往里边插入新对象时发生阻塞。
 * 它会一直处于阻塞之中，直到负责消费的线程从队列中拿走一个对象。 负责消费的线程将会一直从该阻塞队列中拿出对象。
 * 如果消费线程尝试去从一个空的队列中提取对象的话，这个消费线程将会处于阻塞之中，直到一个生产线程把一个对象丢进队列。
 */
public class _01_BlockingQueue {

    @Test
    public void testArrayBlockingQueue() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }

    @Test
    public void testDelayQueue() throws InterruptedException {
        BlockingQueue<Delayed> queue = new DelayQueue<>();
        Delayed element1 = new DelayedElement();

        queue.put(element1);


        Instant instant = Instant.now();
        Delayed element2 = queue.take();
        System.out.println(Duration.between(instant, Instant.now()).toMillis());
        System.out.println(element2.getDelay(TimeUnit.SECONDS));


        Thread.sleep(4000);
    }

    class DelayedElement implements Delayed {

        @Override
        public long getDelay(TimeUnit unit) {
            return 20;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    @Test
    public void testDelayed() {
        DelayedElement delayedElement = new DelayedElement();

        System.out.println(TimeUnit.DAYS.toSeconds(1));
        System.out.println(delayedElement.getDelay(TimeUnit.HOURS));

    }

    class Producer implements Runnable {
        BlockingQueue<String> queue = null;
        Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                queue.put("1");
                Thread.sleep(1000);
                queue.put("2");
                Thread.sleep(1000);
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {
        BlockingQueue<String> queue = null;
        Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
