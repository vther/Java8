package com.vther.java.lambda;


import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class _04_MethodReference {
    public static void main(String[] args) throws IOException {
        // 方法推导(方法引用)主要有3中类型

        // ---- (1) 指向静态方法的方法引用（例如Integer的parseInt方法，写作Integer::parseInt）。
        // Function<String, Integer> function = s -> Integer.parseInt(s);
        Function<String, Integer> function = Integer::parseInt;

        // ---- (2) 指向任意类型实例方法的方法引用（ 例如String 的length 方法， 写作String::length）。
        //  Function<String, Integer> stringIntegerFunction = s -> s.length();
        Function<String, Integer> function1 = String::length;
        //  BiFunction<String, Integer, Character> biFunction = (s, index) -> s.charAt(index);
        BiFunction<String, Integer, Character> biFunction = String::charAt;
        //  BiPredicate<List<String>, String> contains =  (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> biPredicate = List::contains;

        // ---- (3) 指向现有对象的实例方法的方法引用（例如()->expensiveTransaction.getValue()可以写作expensiveTransaction::getValue)
        List<Apple> list = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
        list.sort(Comparator.comparingLong(Apple::getWeight));
        System.out.println(list);
    }

}

