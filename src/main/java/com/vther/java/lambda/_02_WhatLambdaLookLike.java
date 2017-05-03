package com.vther.java.lambda;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class _02_WhatLambdaLookLike {

    public static void main(String[] args) {
        /*  1, lambda表达式形如：

             (Type param1,Type param2, ...,Type paramN) -> {
                 statement1;
                 statement2;
                 //.............
                 return statementM;
             }
         */
        BiFunction<String, Double, Integer> biFunction = (String s, Double d) -> {
            System.out.println(s);
            System.out.println(d);
            return 100;
        };
        /*  2, 当lambda表达式放在具体的函数里时，可以省略掉类型：

              (param1,param2, ...,paramN) -> {
                 statement1;
                 statement2;
                 //.............
                 return statementM;
             }
         */
        BiFunction<String, Double, Integer> biFunction2 = (s, d) -> {
            System.out.println(s);
            System.out.println(d);
            return 100;
        };
        /*  3, 当lambda表达式的参数个数只有一个，可以省略小括号。lambda表达式简写为：

            param1 -> {
              statement1;
              statement2;
              //.............
              return statementM;
            }
         */
        Function<String, Integer> function = s -> {
            System.out.println("I am in lambda");
            return 100;
        };
        /*  4, 当lambda表达式只包含一条语句时，可以省略大括号、return和语句结尾的分号。lambda表达式简化为：

            param1 -> statement
         */
        Function<String, Integer> function2 = s -> 100;
        /*  5, 使用Method Reference(具体语法后面介绍)，可以进一步简化

            如把Consumer<String> consumer = x -> System.out.println(x);可以简化为
         */

        Consumer<String> consumer = System.out::println;
    }
}
