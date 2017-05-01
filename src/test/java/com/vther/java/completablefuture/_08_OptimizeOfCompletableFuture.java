package com.vther.java.completablefuture;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class _08_OptimizeOfCompletableFuture {


    /**
     * 调整线程池的大小
     * 《Java并发编程实战》（http://mng.bz/979c）一书中，Brian Goetz和合著者们为线程池大小
     * 的优化提供了不少中肯的建议。这非常重要，如果线程池中线程的数量过多，最终它们会竞争
     * 稀缺的处理器和内存资源，浪费大量的时间在上下文切换上。反之，如果线程的数目过少，正
     * 如你的应用所面临的情况，处理器的一些核可能就无法充分利用。Brian Goetz建议，线程池大
     * 小与处理器的利用率之比可以使用下面的公式进行估算：
     * Nthreads = NCPU * UCPU * (1 + W/C)
     * 其中：
     * ❑NCPU是处理器的核的数目，可以通过Runtime.getRuntime().availableProcessors()得到
     * ❑UCPU是期望的CPU利用率（该值应该介于0和1之间）
     * ❑W/C是等待时间与计算时间的比率
     * <p>
     * 你的应用99%的时间都在等待商店的响应，所以估算出的W/C比率为100。这意味着如果你
     * 期望的CPU利用率是100%，你需要创建一个拥有400个线程的线程池。实际操作中，如果你创建
     * 的线程数比商店的数目更多，反而是一种浪费，因为这样做之后，你线程池中的有些线程根本没
     * 有机会被使用。出于这种考虑，我们建议你将执行器使用的线程数，与你需要查询的商店数目设
     * 定为同一个值，这样每个商店都应该对应一个服务线程。不过，为了避免发生由于商店的数目过
     * 多导致服务器超负荷而崩溃，你还是需要设置一个上限，比如100个线程。代码清单如下所示。
     */

    private static final List<String> SHOPS = Arrays.asList("Shop-1", "Shop-2", "Shop-3", "Shop-4", "Shop-5");
    //  Executors.newFixedThreadPool 的第一个参数就是 N_THREADS
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(SHOPS.size(), r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });
    private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

    public static void main(String[] args) {
        //execute("sequential", () -> findPricesSequential("iphone"));
        execute("parallel", () -> findPricesParallel("iphone"));
        execute("composed CompletableFuture", () -> findPricesFuture("iphone"));
        //printPricesStream("iphone");
    }

    // 打印时间和结果
    private static void execute(String msg, Supplier<List<String>> supplier) {
        long start = System.nanoTime();
        System.out.println(supplier.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " s");
    }

    // 获取价格
    private static Double price(String productName) {
        TimeUtils.sleep1Seconds();
        Random random = new Random(productName.charAt(0) * productName.charAt(1) * productName.charAt(2));
        return random.nextDouble() * productName.charAt(0) + productName.charAt(1);
    }

    // 打折后，格式化为易读的字符串
    private static String discount(Double price, String product) {
        TimeUtils.sleep1Seconds();
        Random random = new Random();
        return product + "'s price=" + format(price * random.nextDouble());
    }

    // 顺序执行的 stream
    private static List<String> findPricesSequential(String product) {
        return SHOPS.stream()
                .map(shop -> _08_OptimizeOfCompletableFuture.price(product))
                .map(price -> _08_OptimizeOfCompletableFuture.discount(price, product))
                .collect(Collectors.toList());
    }

    // 并序执行的 stream
    private static List<String> findPricesParallel(String product) {
        return SHOPS.parallelStream()
                .map(shop -> _08_OptimizeOfCompletableFuture.price(product))
                .map(price -> _08_OptimizeOfCompletableFuture.discount(price, product))
                .collect(Collectors.toList());
    }

    // 使用 CompletableFuture 并序执行
    private static List<String> findPricesFuture(String product) {
        return SHOPS.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> _08_OptimizeOfCompletableFuture.price(product), EXECUTOR))
                .map(future -> future.thenCompose(price -> CompletableFuture.supplyAsync(() -> _08_OptimizeOfCompletableFuture.discount(price, product), EXECUTOR)))
                .parallel()//这一行代码如果不加，将会顺序执行
                //.collect(Collectors.toList()).stream()//或者使用这个，也会并序执行
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // 使用 CompletableFuture allOf 并序执行
    private static void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture
                .allOf(SHOPS.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> _08_OptimizeOfCompletableFuture.price(product), EXECUTOR))
                        .map(future -> future.thenCompose(price -> CompletableFuture.supplyAsync(() -> _08_OptimizeOfCompletableFuture.discount(price, product), EXECUTOR)))
                        .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                        .toArray(CompletableFuture[]::new))
                .join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " s");
    }

    private static double format(double number) {
        synchronized (formatter) {
            return new Double(formatter.format(number));
        }
    }
    /*
    *并行——使用流还是CompletableFutures？
    *目前为止，你已经知道对集合进行并行计算有两种方式：要么将其转化为并行流，利用map
    *这样的操作开展工作，要么枚举出集合中的每一个元素，创建新的线程，在Completable-
    *Future内对其进行操作。后者提供了更多的灵活性，你可以调整线程池的大小，而这能帮助
    *你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
    *我们对使用这些API的建议如下。
    *❑如果你进行的是计算密集型的操作，并且没有I/O，那么推荐使用Stream接口，因为实
    *现简单，同时效率也可能是最高的（如果所有的线程都是计算密集型的，那就没有必要
    *创建比处理器核数更多的线程）。
    *❑反之，如果你并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用
    *CompletableFuture灵活性更好，你可以像前文讨论的那样，依据等待/计算，或者
    *W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是，处理流的
    *流水线中如果发生I/O等待，流的延迟特性会让我们很难判断到底什么时候触发了等待
     */
}
