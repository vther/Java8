package com.vther.java.completablefuture;


public class TimeUtils {
    public static void sleep5Seconds() {
        sleepSeconds(5);
    }

    public static void sleep3Seconds() {
        sleepSeconds(3);
    }

    public static void sleep2Seconds() {
        sleepSeconds(2);
    }


    public static void sleep1Seconds() {
        sleepSeconds(1);
    }

    public static void sleepSeconds(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
