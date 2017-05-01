package com.vther.java.completablefuture;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _05_ExecutorOfCompletableFuture {

    public static void main(String[] args) throws InterruptedException {

        // 这种方式，主线程死的时候，子线程会死
        /*
        CompletableFuture.supplyAsync(_03_CompletableFuture::get)
                .whenComplete((v, t) -> {
                    Optional.of(v).ifPresent(System.out::println);
                });
        */

        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });
        CompletableFuture.supplyAsync(_03_CompletableFuture::get, executor)
                .whenComplete((v, t) -> Optional.of(v).ifPresent(System.out::println));
        executor.shutdown();
        System.out.println("====i am no ---block----");
    }
}
