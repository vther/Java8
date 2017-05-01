package com.vther.java.completablefuture;


class TimeUtils {
    static void sleep5Seconds() {
        sleepSeconds(5);
    }

    static void sleep2Seconds() {
        sleepSeconds(2);
    }

    static void sleep1Seconds() {
        sleepSeconds(1);
    }

    private static void sleepSeconds(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
