package com.vther.java.stream;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExamples {
    @Test
    public void example1() {
//        List<String> list = Collections.emptyList();
        List<String> list = Arrays.asList("aa", "bb", "cc");
        System.out.println(list.stream().count());
        System.out.println(list.parallelStream().count());
        System.out.println(list.stream().map(d -> 1).reduce(Integer::sum));
    }


    /*
    给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？例如，给定[1,2,3,4,5]，
    应该返回[1,4,9,16,25]。
     */
    @Test
    public void example2() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList()).forEach(System.out::println);
    }

    /*
    给定两个数字列表，如何返回所有的数对呢？例如，给定列表[1, 2, 3]和列表[3, 4]，应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
    为简单起见，你可以用有两个元素的数组来代表数对。
     */
    @Test
    public void example3() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(Collectors.toList()).forEach(a -> System.out.print(a[0] + " " + a[1] + "\r\n"));
    }

    /*
   如何扩展前一个例子，只返回总和能被3整除的数对呢？例如(2, 4)和(3, 3)是可以的。
     */
    @Test
    public void example4() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j})
                ).filter(a -> (a[0] + a[1]) % 3 == 0)
                .collect(Collectors.toList()).forEach(a -> System.out.print(a[0] + " " + a[1] + "\r\n"));

        numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j})
                )
                .collect(Collectors.toList()).forEach(a -> System.out.print(a[0] + " " + a[1] + "\r\n"));
    }
}
