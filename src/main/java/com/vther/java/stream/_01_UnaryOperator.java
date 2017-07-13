package com.vther.java.stream;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class _01_UnaryOperator {

    //  UnaryOperator 是特殊的Function，接收T返回T，可以用于自身变化
    @Test
    public void example1() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        UnaryOperator<String> operator = a -> a + a;
        list.replaceAll(operator);
        System.out.println(list);
    }

    @Test
    public void example2() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        UnaryOperator<String> operator = String::toUpperCase;
        list.replaceAll(operator);
        System.out.println(list);
    }

    // UnaryOperator.identity() 用于返回自己
    @Test
    public void example3() {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        list.replaceAll(UnaryOperator.identity());
        System.out.println(list);
    }
}
