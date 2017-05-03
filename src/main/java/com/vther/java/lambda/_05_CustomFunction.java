package com.vther.java.lambda;


import java.io.IOException;
import java.util.function.BiFunction;

public class _05_CustomFunction {

    public static void main(String[] args) throws IOException {

        // 两个参数的构造函数
        BiFunction<Long, String, Apple> biFunction = Apple::new;
        System.out.println("biFunction: Apple=" + biFunction.apply(123L, "name"));

        // 三个参数的构造函数，自定义了ThreeFunction
        ThreeFunction<String, Long, String, ComplexApple> threeFunction = ComplexApple::new;
        System.out.println("threeFunction: Apple=" + threeFunction.apply("apple", 123L, "name"));
    }

    @FunctionalInterface
    public interface ThreeFunction<T, U, K, R> {
        R apply(T t, U u, K k);
    }
}
