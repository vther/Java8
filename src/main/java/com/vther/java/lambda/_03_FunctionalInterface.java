package com.vther.java.lambda;


import org.junit.Test;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class _03_FunctionalInterface {


    // Function 接收一个参数，返回一个参数
    @Test
    public void testFunction() {
        Function<Integer, Integer> f = x -> x + 1;
        System.out.println("[x -> x + 1] 's result = " + f.apply(7));
        Function<Integer, Integer> g = x -> x * 2;
        System.out.println("[x -> x * 2]'s result = " + g.apply(7));

        Function<Integer, Integer> h1 = f.andThen(g); // 相当于 g(f(x))
        Function<Integer, Integer> h2 = f.compose(g); // 相当于 f(g(x))
        System.out.println(h1.apply(1));
        System.out.println(h2.apply(1));
    }

    // Consumer 接收一个参数
    @Test
    public void testConsumer() {
        Consumer<String> c1 = System.out::println;
        Consumer<String> c2 = System.out::println;
        c1.accept("Hello ");
        c2.accept("World");

        Consumer<String> c3 = c1.andThen(c2); // return (T t) -> { accept(t); after.accept(t); };
        c3.accept("Test Lambda!");// 注意 Test Lambda!会被执行两次，但是c3不会输出Hello World
    }

    // Supplier 返回一个参数
    @Test
    public void testSupplier() {
        Supplier<Apple> supplier = Apple::new;
        System.out.println(supplier.get());
    }

    // Predicate 相当于Function<Object, Boolean>
    @Test
    public void testPredicate() {
        Predicate<Apple> redPredicate = apple -> Objects.equals("Red", apple.getColor());
        Apple testApple = new Apple();
        testApple.setColor("Red");
        testApple.setWeight(140);
        System.out.println("redPredicate -> " + redPredicate.test(testApple));
        // 取反
        Predicate<Apple> notRedPredicate = redPredicate.negate();
        System.out.println("notRedPredicate -> " + notRedPredicate.test(testApple));
        // 与
        Predicate<Apple> redAndHeavyApple = redPredicate.and(apple -> apple.getWeight() > 150);
        System.out.println("redAndHeavyApple -> " + redAndHeavyApple.test(testApple));
        // 或
        Predicate<Apple> redOrGreenApple = redPredicate.or(apple -> Objects.equals("Green", apple.getColor()));
        System.out.println("redOrGreenApple -> " + redOrGreenApple.test(testApple));

    }
}
