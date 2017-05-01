package com.vther.java.completablefuture;

import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _07_BothEitherAnyOfCompletableFuture {


    /**
     * 5秒钟后返回一个随机数（模拟一个耗时的任务）
     */
    private static double get() {
        Random random = new Random(System.currentTimeMillis());
        TimeUtils.sleep2Seconds();
        return random.nextDouble();
    }

    // runAfterBoth 执行完两个CompletableFuture，再执行一步Runnable
    @Test
    public void testRunAfterBoth() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName() + " is running.");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                })
                .runAfterBoth(CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName() + " is running.");
                    return 2;
                }), () -> System.out.println("done"));

        TimeUtils.sleep5Seconds();
    }

    // runAfterEither 执行完任何一个CompletableFuture，再执行一步Runnable
    @Test
    public void testRunAfterEither() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("I am future 1");
                    TimeUtils.sleep2Seconds();
                    return "future1";
                })
                .runAfterEither(CompletableFuture.supplyAsync(() -> {
                    System.out.println("I am future 2");
                    TimeUtils.sleep1Seconds();
                    return "future2";
                }), () -> System.out.println("done"));

        TimeUtils.sleep5Seconds();
    }

    // applyToEither 两个CompletableFuture，一个执行完毕就可以
    @Test
    public void testApplyToEither() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("I am future 1");
                    TimeUtils.sleep2Seconds();
                    return "future1";
                })
                .applyToEither(CompletableFuture.supplyAsync(() -> {
                    System.out.println("I am future 2");
                    TimeUtils.sleep1Seconds();
                    return "future2";
                }), v -> "result from " + v)
                .thenAccept(System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // acceptEither 两个CompletableFuture，一个执行完毕就可以，和applyToEither的区别就是接收一个Consumer
    @Test
    public void testAcceptEither() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("I am future 1");
                    TimeUtils.sleep2Seconds();
                    return "future1";
                })
                .acceptEither(CompletableFuture.supplyAsync(() -> {
                    System.out.println("I am future 2");
                    TimeUtils.sleep1Seconds();
                    return "future2";
                }), System.out::println);

        TimeUtils.sleep5Seconds();
    }

    // anyOf 多个CompletableFuture，一个执行完毕就可以
    @Test
    public void testAnyOf() {
        List<CompletableFuture<Double>> collect = Stream.of(1, 2, 3, 4)
                .map(i -> CompletableFuture.supplyAsync(_07_BothEitherAnyOfCompletableFuture::get))
                .collect(Collectors.toList());

        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()]))
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("done"));

        TimeUtils.sleep5Seconds();
    }


}
