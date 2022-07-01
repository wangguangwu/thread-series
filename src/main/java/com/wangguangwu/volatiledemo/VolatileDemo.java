package com.wangguangwu.volatiledemo;

/**
 * @author wangguangwu
 */
public class VolatileDemo {

    private static volatile boolean isOver = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!isOver) {
                System.out.println(isOver);
            }
            System.out.println(isOver);
        }).start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 使用 volatile 关键字修饰
        // 修改过后，会直接回写到主内存中，并且其他线程中的 isOver 变量也会失效，需要重新去主内存中获取
        isOver = true;
    }

}
