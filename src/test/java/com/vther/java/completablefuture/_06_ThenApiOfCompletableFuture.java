package com.vther.java.completablefuture;


import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class _06_ThenApiOfCompletableFuture {


    // thenApply 执行完毕一个CompletableFuture，再对其执行结果进行下一步操作
    @Test
    public void testThenApply() {
        CompletableFuture.supplyAsync(_06_ThenApiOfCompletableFuture::get)
                .thenApply(i -> Integer.sum(i, 10))
                .thenAccept(System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // handle 比 thenApply 多出来处理异常的部分
    @Test
    public void testHandle() {
        CompletableFuture.supplyAsync(_06_ThenApiOfCompletableFuture::get)
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // thenCompose 执行完毕一个CompletableFuture，再对其进行下一个CompletableFuture操作
    @Test
    public void testThenCompose() {
        CompletableFuture.supplyAsync(_06_ThenApiOfCompletableFuture::get)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // thenCombine 执行完毕一个CompletableFuture，再执行另外一个CompletableFuture，最后在对两个的执行结果执行BiFunction
    @Test
    public void testThenCombine() {
        CompletableFuture.supplyAsync(_06_ThenApiOfCompletableFuture::get)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2)
                .thenAccept(System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // thenAcceptBoth 需要两个都正常执行完毕，才能得到结果
    @Test
    public void testThenAcceptBoth() {
        CompletableFuture.supplyAsync(_06_ThenApiOfCompletableFuture::get)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> {
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                });

        TimeUtils.sleep5Seconds();
    }
    /**
     * 5秒钟后返回一个随机数（模拟一个耗时的任务）
     */
    private static int get() {
        Random random = new Random(System.currentTimeMillis());
        TimeUtils.sleep2Seconds();
        return (int) random.nextDouble();
    }
}
