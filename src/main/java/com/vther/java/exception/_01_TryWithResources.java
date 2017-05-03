package com.vther.java.exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * try-with-resources
 * --- try-with-resources语句是一个声明一个或多个资源的try语句。
 * --- 一个资源作为一个对象，必须在程序结束之后关闭。
 * --- try-with-resources语句确保在语句的最后每个资源都被关闭，
 * --- 任何实现了Java.lang.AutoCloseable和java.io.Closeable的对象都可以使用try-with-resource来实现异常处理和关闭资源。
 * <p>
 * 通过上面的对比，try-with-resources的优点
 * 1.代码精炼
 * 2.代码更完全。
 * --- 在出现资源泄漏的程序中，很多情况是开发人员没有或者开发人员没有正确的关闭资源所导致的。
 * --- JDK1.7之后采用try-with-resources的方式，则可以将资源关闭这种与业务实现没有很大直接关系的工作交给JVM完成。
 * --- 省去了部分开发中可能出现的代码风险。
 */
public class _01_TryWithResources {

    private static final String FILE_PATH = "E:\\Develop\\intel-workspace\\Java8\\src\\main\\resources\\data.txt";

    public static void main(String[] args) {
        try {
            System.out.println(readFirstLingFromFileBeforeJDK7(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(readFirstLineFromFileAfterJDK7(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JDK1.7之前我们必须在finally块中手动关闭资源，否则会导致资源的泄露
    private static String readFirstLingFromFileBeforeJDK7(String path) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();//必须在这里关闭资源
        }
        return null;
    }

    //  JDK1.7之后就可以使用try-with-resources,不需要我们在finally块中手动关闭资源
    private static String readFirstLineFromFileAfterJDK7(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
}