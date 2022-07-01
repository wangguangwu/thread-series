package com.wangguangwu.volatiledemo;

/**
 * @author wangguangwu
 */
public class VolatileDemo2 {

    private static volatile boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!flag) {

            }
            System.out.println("execution end");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
        }).start();
    }

}
