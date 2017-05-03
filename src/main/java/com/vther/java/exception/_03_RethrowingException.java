package com.vther.java.exception;


public class _03_RethrowingException {

    public static void main(String[] args) {
        try {
            rethrowExceptionAfterJDK7();
        } catch (Exception e) {
            e.printStackTrace();//com.vther.java.exception._02_RethrowingException$FirstException
        }
        try {
            rethrowExceptionBeforeJDK7("First");
        } catch (Exception e) {
            e.printStackTrace();//com.vther.java.exception._02_RethrowingException$FirstException
        }
    }

    private static void rethrowExceptionAfterJDK7() throws FirstException, SecondException, Exception {
        try {
            throw new RuntimeException();// 逻辑代码
        } catch (Exception e) {
            throw e;
        }
    }

    // 在JDK1.7以前的版本，在方法声明中声明抛出的异常如果在方法体内没有抛出时不被允许的，如下：
    private static void rethrowExceptionBeforeJDK7(String exceptionName) throws Exception {
        try {
            if (exceptionName.equals("First")) {
                throw new FirstException();             //如果异常名称为"First"，则抛出异常一
            } else {
                throw new SecondException();                //否则的话，则抛出异常二
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static class FirstException extends Exception {
    }

    private static class SecondException extends Exception {
    }

}