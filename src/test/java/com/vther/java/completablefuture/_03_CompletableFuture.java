package com.vther.java.completablefuture;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class _03_CompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        _01_CompletableFuture_New();    // 使用new的方式，使用CompletableFuture
        _02_CompletableFuture_Supply(); // 使用supply的方式，使用CompletableFuture
        //Thread.sleep(10000L);// 使用 _02_CompletableFuture_Supply 时，需要放开这个，因为函数内部没有设置Executor，所以需要设置延时

        _03_CompletableFuture_Pipeline();// 使用pipeline的形式，使用CompletableFuture
        _04_CompletableFuture_Multi();// 使用CompletableFuture，同时执行多个任务


    }


    private static void _01_CompletableFuture_New() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> future.complete(_03_CompletableFuture.get())).start();
        System.out.println("main thread -> I am not blocked");
        future.whenComplete((v, t) -> {
            Optional.ofNullable(v).ifPresent(System.out::println);
            Optional.ofNullable(t).ifPresent(Throwable::printStackTrace);
        });
        System.out.println("main thread -> I am not blocked");
    }

    private static void _02_CompletableFuture_Supply() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(_03_CompletableFuture::get);
        System.out.println("main thread -> I am not blocked");
        // 因为这里不是pipeline的写法，所以需要设置Executor才可以看到正常的执行效果
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });
        System.out.println("main thread -> I am not blocked");
    }


    private static void _03_CompletableFuture_Pipeline() throws ExecutionException, InterruptedException {
        Double value = CompletableFuture.supplyAsync(_03_CompletableFuture::get)
                .whenComplete((v, t) -> System.out.println("result is -> " + v))
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> i + 10))
                .get();
        System.out.println(value);
    }

    private static void _04_CompletableFuture_Multi() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        System.out.println(integers);
        // 一个整数数组，乘以耗时任务的随机数
        List<CompletableFuture<Double>> futures = integers
                .stream()
                .map(d -> CompletableFuture.supplyAsync(() -> d * _03_CompletableFuture.get()))
                .collect(Collectors.toList());
        List<Double> collect = futures.stream().parallel().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("time costs -> " + (System.currentTimeMillis() - start) + "ms");

        //List<Double> collect2 = integers
        //        .stream()
        //        .map(d -> CompletableFuture.supplyAsync(() -> d * _03_CompletableFuture.get()))
        //        .parallel()
        //        .map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 5秒钟后返回一个随机数（模拟一个耗时的任务）
     */
    static double get() {
        Random random = new Random(System.currentTimeMillis());
        TimeUtils.sleep5Seconds();
        return random.nextDouble();
    }
}
