package com.vther.java.lambda;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class _01_WhyUseLambda {
    public static void main(String[] args) {
        List<Apple> apples1 = Arrays.asList(
                new Apple(100, "Green"),
                new Apple(200, "Red"),
                new Apple(50, "Yellow")
        );
        // 使用匿名内部类
        apples1.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return Long.compare(a1.getWeight(), a2.getWeight());
            }
        });
        System.out.println("apples1 -> " + apples1);


        // 使用lambda表达式
        List<Apple> apples2 = Arrays.asList(
                new Apple(100, "Green"),
                new Apple(200, "Red"),
                new Apple(50, "Yellow")
        );
        apples2.sort(Comparator.comparingLong(Apple::getWeight));
        System.out.println("apples2 -> " + apples2);
    }
}
