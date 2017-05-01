package com.vther.java.completablefuture;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Future 接口的局限性<br/>
 * <p>
 * <p>通过第一个例子，我们知道Future接口提供了方法来检测异步计算是否已经结束（使用isDone方法），等待异步操作结束，以及获取计算的结果。
 * 但是这些特性还不足以让你编写简洁的并发代码。比如，我们很难表述Future结果之间的依赖性； 从文字描述上这很简单，
 * “当长时间计算任务完成时，请将该计算的结果通知到另一个长时间运行的计算任务，这两个计算任务都完成后，将计算的结果与另一个查询操作结果合并”。
 * 但是，使用Future中提供的方法完成这样的操作又是另外一回事。<br/>
 * 这也是我们需要更具描述能力的特性的原因，比如下面这些。<br/>
 * <p>
 *  将两个异步计算合并为一个——这两个异步计算之间相互独立，同时第二个又依赖于第一个的结果。<br/>
 *  等待Future集合中的所有任务都完成。<br/>
 *  仅等待Future集合中最快结束的任务完成（有可能因为它们试图通过不同的方式计算同一个值），并返回它的结果。<br/>
 *  通过编程方式完成一个Future任务的执行（即以手工设定异步操作结果的方式）。<br/>
 *  应对Future的完成事件（即当Future的完成事件发生时会收到通知，并能使用Future计算的结果进行下一步的操作，不只是简单地阻塞等待操作的结果）。<br/>
 * <p>
 * 这一章中，你会了解新的CompletableFuture类（它实现了Future接口）如何利用Java 8的新特性以更直观的方式将上述需求都变为可能。
 * Stream和CompletableFuture的设计都遵循了类似的模式：它们都使用了Lambda表达式以及流水线的思想。
 * 从这个角度，你可以说CompletableFuture和Future的关系就跟Stream和Collection的关系一样。
 */
public class _01_Future {

    /*
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        // ExecutorService使用的线程默认是非守护线程，要让其结束掉，有两种方式：
        // ---- 1，设为守护线程 - 2，显式调用其shutdown()方法

        //ExecutorService executorService = Executors.newSingleThreadExecutor((r) -> {
        //    Thread thread = new Thread(r);
        //    thread.setDaemon(true);
        //    return thread;
        //});
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUtils.sleep5Seconds();
                return "I am finished.";
            }
        });

        System.out.println("main thread -> I am not blocked");


        // System.out.println(future.get());
        //String value = future.get(10, TimeUnit.MICROSECONDS);
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        System.out.println("result from thread -> " + future.get());
        executorService.shutdown();// 这里采用方式二结束ExecutorService
    }
    */

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // ExecutorService使用的线程默认是非守护线程，要让其结束掉，有两种方式：
        // ---- 1，设为守护线程(此时子线程里面的任务可能还没执行完就挂了)
        // ---- 2，设为非守护线程，但显式调用其shutdown()方法
        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });
        executor.execute(() -> {
            TimeUtils.sleep5Seconds();
            System.out.println("inside thread");
        });
        executor.shutdown();
        //executor.shutdownNow();//shutdownNow会立刻结束掉子线程，子线程会抛出InterruptedException
    }

}
