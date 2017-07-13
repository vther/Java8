package com.vther.java.stream;


import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

public class _02_BinaryOperator {
    //  BinaryOperator 是特殊的BiFunction，接收T,T返回T，用于减少操作
    @Test
    public void example1() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        BinaryOperator<String> operator = String::concat; // 相当于 (a, b) -> a.concat(b);
        list.stream().reduce(operator).ifPresent(System.out::println);
    }

    @Test
    public void example2() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        System.out.println(list.stream().map(d -> 1).reduce(Integer::sum));
    }

    @Test
    public void example3() {
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        Comparator<Long> comparator = Comparator.comparingLong(a -> a);
        BinaryOperator<Long> longBinaryOperator = BinaryOperator.maxBy(comparator);
        list.stream().reduce(longBinaryOperator).ifPresent(System.out::println);

        List<String> list2 = Arrays.asList("fa", "abc", "test");
        Comparator<String> comparator2 = Comparator.comparing(String::toUpperCase);// 按照大写字母进行字典顺序比较
        //Comparator<String> comparator2 = Comparator.comparingLong(String::length);// 按照长度进行比较
        BinaryOperator<String> stringBinaryOperator = BinaryOperator.minBy(comparator2);
        list2.stream().reduce(stringBinaryOperator).ifPresent(System.out::println);
    }

}
